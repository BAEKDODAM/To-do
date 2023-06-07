package com.practice.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoResponseDto {
    private long id;
    private String title;
    private int todoOrder;
    private boolean completed;
}
