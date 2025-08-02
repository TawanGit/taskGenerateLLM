package tech.tawan.taskGenerate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.tawan.taskGenerate.dto.TaskRequest;
import tech.tawan.taskGenerate.service.AIService;

@RestController
@RequestMapping("/api/tasks")
public class TaskGenApplication {
    private final AIService aiService;

    public TaskGenApplication(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateTask(@RequestBody TaskRequest request) {
        String task = aiService.generateTask(request.getCategory());
        return ResponseEntity.ok(task);
    }
}
