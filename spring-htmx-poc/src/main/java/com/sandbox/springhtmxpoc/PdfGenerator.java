package com.sandbox.springhtmxpoc;

import org.springframework.stereotype.Component;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

@Component
public class PdfGenerator {

    private final RandomGenerator randomGenerator = RandomGeneratorFactory.getDefault().create();

    public void generatePdf(ProgressListener listener) {
        System.out.println("Generating PDF...");
        int progress = 0;
        listener.onProgress(progress);
        do {
            sleep();
            progress += randomGenerator.nextInt(10);
            System.out.println("Progress:" + progress);
            listener.onProgress(progress);
        } while (progress < 100);
        System.out.println("Done!");
        listener.onCompletion();
    }

    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
    }


}

