package com.example.module9.controller;

import com.example.module9.JobApplicationTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @GetMapping("/hello")
    public ResponseEntity<String> generate(
            @RequestParam(defaultValue = "Explain Spring Boot") String message) {

        try {
            String response = chatClient.prompt()
                    .tools(new JobApplicationTools())
                    .system("""
    You are a friendly Java tutor teaching beginners.
    Explain concepts in simple language with short paragraphs.
    Do not include code unless the user asks for code.
    If code is requested, format it clearly.
    Do not output literal Markdown code fences unless code is requested.
    """)
                    .user(message)
                    .call()
                    .content();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("The AI service is temporarily unavailable. Please try again later.");
        }
    }


}