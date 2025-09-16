package com.teamread.poc.video_generator;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/video")
public class VideoController {

    private final BedrockService bedrockService;
    private final S3Service s3Service;

    public VideoController(BedrockService bedrockService, S3Service s3Service) {
        this.bedrockService = bedrockService;
        this.s3Service = s3Service;
    }

    @PostMapping("/generate")
    public CompletableFuture<String> generateVideo(@RequestBody String prompt) {
        return bedrockService.createVideoGenerationJob(prompt);
    }

    @GetMapping("/status/{jobId}")
    public CompletableFuture<String> getVideoStatus(@PathVariable String jobId) {
        return bedrockService.getJobStatus(jobId)
                .thenApply(status -> {
                    JSONObject statusJson = new JSONObject(status);
                    if ("Completed".equals(statusJson.getString("status"))) {
                        return s3Service.getPresignedUrl(statusJson.getString("output"));
                    } else {
                        return status;
                    }
                });
    }
}
