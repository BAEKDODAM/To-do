package com.practice.todo.dto;

import com.practice.todo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TodoResponseDto {
    private long todoId;
    private String title;
    //private int todoOrder;
    private Todo.TodoPriority todoPriority;
    private boolean completed;
    private String createdAt;
    private String modifiedAt;

}
