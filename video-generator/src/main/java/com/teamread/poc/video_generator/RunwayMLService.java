package com.teamread.poc.video_generator;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class RunwayMLService {

    private final RestTemplate restTemplate;
    private final RunwayMLConfig config;

    public RunwayMLService(RestTemplate restTemplate, RunwayMLConfig config) {
        this.restTemplate = restTemplate;
        this.config = config;
    }

    public CompletableFuture<String> generateVideo(VideoGenerationRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            String taskId = triggerVideoGeneration(request);
            return pollForVideoResult(taskId);
        });
    }

    private String triggerVideoGeneration(VideoGenerationRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(config.getApiKey());
        headers.set("X-Runway-Version", "2024-11-06");

        VideoRequest videoRequest = new VideoRequest(
                request.getPromptImage(),
                4294967295L, // seed
                "gen4_turbo", // model
                request.getPromptText(),
                5, // duration
                "1280:720", // ratio
                new ContentModeration("auto") // contentModeration
        );

        HttpEntity<VideoRequest> entity = new HttpEntity<>(videoRequest, headers);

        ResponseEntity<TaskResponse> response = restTemplate.postForEntity(config.getApiUrl() + "/image_to_video", entity, TaskResponse.class);

        if (response.getBody() != null) {
            return response.getBody().getTaskId();
        } else {
            throw new RuntimeException("Failed to trigger video generation");
        }
    }

    private String pollForVideoResult(String taskId) {
        while (true) {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(config.getApiKey());
            headers.set("X-Runway-Version", "2024-11-06");
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<VideoResponse> response = restTemplate.exchange(config.getApiUrl() + "/tasks/" + taskId, HttpMethod.GET, entity, VideoResponse.class);

            if (response.getBody() != null) {
                VideoResponse videoResponse = response.getBody();
                if ("succeeded".equalsIgnoreCase(videoResponse.getStatus())) {
                    return videoResponse.getOutput().getVideoUrl();
                } else if ("failed".equalsIgnoreCase(videoResponse.getStatus())) {
                    throw new RuntimeException("Video generation failed");
                }
            }

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Polling was interrupted", e);
            }
        }
    }

    private static class VideoRequest {
        private String promptImage;
        private Long seed;
        private String model;
        private String promptText;
        private Integer duration;
        private String ratio;
        private ContentModeration contentModeration;

        public VideoRequest(String promptImage, Long seed, String model, String promptText, Integer duration, String ratio, ContentModeration contentModeration) {
            this.promptImage = promptImage;
            this.seed = seed;
            this.model = model;
            this.promptText = promptText;
            this.duration = duration;
            this.ratio = ratio;
            this.contentModeration = contentModeration;
        }

        public String getPromptImage() {
            return promptImage;
        }

        public Long getSeed() {
            return seed;
        }

        public String getModel() {
            return model;
        }

        public String getPromptText() {
            return promptText;
        }

        public Integer getDuration() {
            return duration;
        }

        public String getRatio() {
            return ratio;
        }

        public ContentModeration getContentModeration() {
            return contentModeration;
        }
    }

    private static class ContentModeration {
        private String publicFigureThreshold;

        public ContentModeration(String publicFigureThreshold) {
            this.publicFigureThreshold = publicFigureThreshold;
        }

        public String getPublicFigureThreshold() {
            return publicFigureThreshold;
        }
    }

    private static class TaskResponse {
        private String taskId;

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }
    }

    private static class VideoResponse {
        private String status;
        private Output output;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Output getOutput() {
            return output;
        }

        public void setOutput(Output output) {
            this.output = output;
        }
    }

    private static class Output {
        private String videoUrl;

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }
    }
}