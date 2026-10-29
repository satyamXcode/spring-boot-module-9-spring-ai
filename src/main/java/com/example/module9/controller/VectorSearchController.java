
package com.example.module9.controller;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
public class VectorSearchController {

    private final EmbeddingModel embeddingModel;

    public VectorSearchController(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @GetMapping("/vector-search")
    public List<Map<String, Object>> search(
            @RequestParam(defaultValue = "How do I build Java applications?")
            String query) {

        List<String> documents = List.of(
                "Spring Boot helps developers build Java applications.",
                "Dependency injection makes Java applications easier to maintain.",
                "The weather is sunny and warm today."
        );

        float[] queryVector = embeddingModel.embed(query);

        return documents.stream()
                .map(document -> {
                    float[] documentVector = embeddingModel.embed(document);

                    double similarity = cosineSimilarity(
                            queryVector, documentVector);

                    return Map.<String, Object>of(
                            "document", document,
                            "similarity", similarity
                    );
                })
                .sorted(Comparator.comparingDouble(
                        result -> -((Number) result.get("similarity"))
                                .doubleValue()))
                .toList();
    }

    private double cosineSimilarity(float[] a, float[] b) {
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
