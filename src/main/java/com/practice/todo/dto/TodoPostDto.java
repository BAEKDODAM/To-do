package com.practice.todo.dto;

import javax.validation.constraints.NotBlank;

public class TodoPostDto {
    @NotBlank
    private String title;
    private int todoOrder;
    private boolean completed;

    public String getTitle() {
        return title;
    }

    public int getTodoOrder() {
        return todoOrder;
    }

    public boolean getCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed){
        this.completed = completed;
    }
}
