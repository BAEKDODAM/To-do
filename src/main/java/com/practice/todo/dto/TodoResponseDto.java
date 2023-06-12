package com.practice.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TodoResponseDto {
    private long todoId;
    private String title;
    private int todoOrder;
    private boolean completed;
}
