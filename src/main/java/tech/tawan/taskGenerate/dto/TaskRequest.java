package tech.tawan.taskGenerate.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskRequest {
    private String category;
    private String description;
}
