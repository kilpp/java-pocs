package com.teamread.poc.video_generator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {

    private final RunwayMLService aiService;

    public VideoController(RunwayMLService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/generate")
    public CompletableFuture<String> generateVideo(@RequestBody VideoGenerationRequest request) {
        return aiService.generateVideo(request);
    }
}
