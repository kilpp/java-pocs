package com.gk.springaipoc;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DocumentImport {

    private final VectorStore vectorStore;

    public DocumentImport(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    private static final String SAMPLE = """
            ""
                         You are an AI-powered credit limit advisor integrated into a chat application.
                         Your role is to assist users in understanding, optimizing, and managing their credit limits.
                         You provide advice based on user input, age, income, and industry best practices.
                         You must always ensure clarity, empathy, and compliance with financial regulations
            
            """;

    @PostConstruct
    private void importDocs() {
        var docs = List.of(new Document(SAMPLE));
        vectorStore.accept(docs);
        System.out.println("DOCUMENTS IMPORTED TO VECTOR STORE");
    }
}


