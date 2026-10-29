
package com.example.module9.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
public class RagController {

    private final ChatClient chatClient;
    private final EmbeddingModel embeddingModel;

    public RagController(
            ChatClient.Builder builder,
            EmbeddingModel embeddingModel) {
        this.chatClient = builder.build();
        this.embeddingModel = embeddingModel;
    }

    @GetMapping("/rag")
    public String ask(
            @RequestParam(defaultValue =
                    "How do I track a job application?")
            String question) {

        List<String> documents = List.of(
                "Users can add a job application with the company name, role, and application date.",
                "Application statuses can be updated to Applied, Interview, Selected, or Rejected.",
                "Users can review their job applications in the application dashboard."
        );

        float[] queryVector = embeddingModel.embed(question);

        String context = documents.stream()
                .max(Comparator.comparingDouble(
                        document -> similarity(
                                queryVector,
                                embeddingModel.embed(document))))
                .orElse("");

        return chatClient.prompt()
                .system("""
                        Answer using only the supplied context.
                        If the context does not contain the answer,
                        say that the available information is insufficient.
                        """)
                .user("Context:\n" + context
                        + "\n\nQuestion: " + question)
                .call()
                .content();
    }

    private double similarity(float[] a, float[] b) {
        double dot = 0;
        double normA = 0;
        double normB = 0;

        for (int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
            normA += a[i] * a[i];
            normB += b[i] * b[i];
        }

        if (normA == 0 || normB == 0) {
            return 0;
        }

        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
