
package com.example.module9.controller;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

@RestController
public class EmbeddingController {

    private final EmbeddingModel embeddingModel;

    public EmbeddingController(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @GetMapping("/embed")
    public Map<String, Object> embed(
            @RequestParam(defaultValue = "Spring dependency injection") String text) {

        float[] vector = embeddingModel.embed(text);

        return Map.of(
                "text", text,
                "dimensions", vector.length,
                "firstValues", Arrays.copyOf(vector, Math.min(10, vector.length))
        );
    }
}
