package com.practice.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class TodoPostDto {
    @NotBlank
    private String title;
    @NotNull
    private int todoOrder;
    private boolean completed;

    public void setCompleted(boolean completed){
        this.completed = completed;
    }
}
