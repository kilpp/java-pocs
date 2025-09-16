package com.teamread.poc.video_generator;

public class VideoGenerationRequest {
    private String promptText;
    private String promptImage;

    // Getters and Setters
    public String getPromptText() {
        return promptText;
    }

    public void setPromptText(String promptText) {
        this.promptText = promptText;
    }

    public String getPromptImage() {
        return promptImage;
    }

    public void setPromptImage(String promptImage) {
        // Note: RunwayML API requires HTTPS URLs for promptImage.
        this.promptImage = promptImage;
    }
}