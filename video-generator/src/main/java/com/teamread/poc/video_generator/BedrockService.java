package com.teamread.poc.video_generator;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeAsyncClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;


@Service
public class BedrockService {

    private final BedrockRuntimeAsyncClient bedrockRuntimeAsyncClient;

    @Value("${aws.s3.bucket.name}")
    private String s3BucketName;

    public BedrockService(BedrockRuntimeAsyncClient bedrockRuntimeAsyncClient) {
        this.bedrockRuntimeAsyncClient = bedrockRuntimeAsyncClient;
    }

    public CompletableFuture<String> createVideoGenerationJob(String prompt) {
        String modelId = "amazon.nova-reel"; 

        // Construct the request body for the model
        String requestBody = new JSONObject()
                .put("prompt", prompt)
                .put("output_bucket", s3BucketName)
                .toString();

        InvokeModelRequest request = InvokeModelRequest.builder()
                .modelId(modelId)
                .body(SdkBytes.fromUtf8String(requestBody))
                .build();

        return bedrockRuntimeAsyncClient.invokeModel(request)
                .thenApply(InvokeModelResponse::body)
                .thenApply(SdkBytes::asUtf8String)
                .thenApply(responseBody -> new JSONObject(responseBody).getString("jobId"));
    }

    public CompletableFuture<String> getJobStatus(String jobId) {
        // This is a simplified example. In a real-world scenario, you would use the
        // GetModelInvocationJob API to get the status of the job.
        return CompletableFuture.completedFuture("{\"status\":\"Completed\",\"output\":\"dummy-key\"}");
    }
}
