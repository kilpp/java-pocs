package com.sandbox.springhtmxpoc.controller;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.sandbox.springhtmxpoc.PdfGenerator;
import com.sandbox.springhtmxpoc.ProgressListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Collection;

@Controller
public class SseController {

    private final Multimap<String, SseEmitter> sseEmitters = MultimapBuilder.hashKeys().arrayListValues().build();
    private final PdfGenerator pdfGenerator;

    public SseController(PdfGenerator pdfGenerator) {
        this.pdfGenerator = pdfGenerator;
    }

    @GetMapping("/progress-events")
    public SseEmitter progressEvents() {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sseEmitters.put("TEST", sseEmitter);
        System.out.println("Adding SseEmitter for user: " + "TEST");
        sseEmitter.onCompletion(() -> System.out.println("SseEmitter is completed"));
        sseEmitter.onTimeout(() -> System.out.println("SseEmitter is timed out"));
        sseEmitter.onError((ex) -> System.out.println("SseEmitter got error:" + ex));

        return sseEmitter;

    }

    @GetMapping("/")
    public String generatePdf() {
        return "index";
    }

    @PostMapping("/")
    public String generatePdfPost() {
        Collection<SseEmitter> sseEmitter = sseEmitters.get("TEST");
        pdfGenerator.generatePdf(new SseEmitterProgressListener(sseEmitter));

        return "index";
    }

    static class SseEmitterProgressListener implements ProgressListener {
        private final Collection<SseEmitter> sseEmitters;

        public SseEmitterProgressListener(Collection<SseEmitter> sseEmitter) {
            this.sseEmitters = sseEmitter;
        }

        @Override
        public void onProgress(int value) {
            String html =
                    """
                            <div class="progress">
                                <div class="progress-bar"
                                    role="progressbar"
                                    style="width: %s%%"
                                    aria-valuenow="25"
                                    aria-valuemin="0"
                                    aria-valuemax="100">
                                </div>
                            </div>
                            """.formatted(value);
            sendToAllClients(html);
        }

        @Override
        public void onCompletion() {
            String html = "<div><a href=\"#\">Download PDF</div>";
            sendToAllClients(html);
        }

        private void sendToAllClients(String html) {
            try {
                sseEmitters.stream().findFirst().orElseThrow().send(html);
            } catch (Exception ignore) {

            }
        }
    }

}
