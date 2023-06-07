package com.practice.todo.dto;

import javax.validation.constraints.NotBlank;

public class TodoPatchDto {
    private long id;
    private String title;
    private int todoOrder;
    private boolean completed;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTodoOrder() {
        return todoOrder;
    }

    public void setTodoOrder(int todoOrder) {
        this.todoOrder = todoOrder;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
