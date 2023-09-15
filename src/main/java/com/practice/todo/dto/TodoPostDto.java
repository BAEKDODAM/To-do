package com.practice.todo.dto;

import com.practice.todo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class TodoPostDto {
    @NotBlank
    private String title;
    //@NotNull
    //private int todoOrder;
    //private boolean completed;
    private Todo.TodoPriority todoPriority;
}
