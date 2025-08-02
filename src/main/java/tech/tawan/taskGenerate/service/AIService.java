package tech.tawan.taskGenerate.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class AIService {

    private final WebClient webClient;

    @Value("${API_KEY}")
    private String API_KEY;

    @Value("${prompt}")
    private String promptTemplate;

    public AIService(@Value("${AI_API_URL}") String apiUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public String generateTask(String category) {
        String prompt = this.promptTemplate + category  ;

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of(
                                "role", "user",
                                "parts", List.of(
                                        Map.of("text", prompt)
                                )
                        )
                )
        );

        Map response = webClient.post()
                .uri(uriBuilder -> uriBuilder.queryParam("key", API_KEY).build())
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        // Gemini returns a 'candidates' array with the content inside
        List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
        if (candidates == null || candidates.isEmpty()) {
            throw new RuntimeException("No response candidates from Gemini API");
        }
        Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
        List<Map<String, String>> parts = (List<Map<String, String>>) content.get("parts");

        return parts.get(0).get("text");
    }
}
