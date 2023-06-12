package com.practice.todo.dto;

import lombok.Getter;

@Getter
public class TodoPatchDto {
    private long todoId;
    private String title;
    private int todoOrder;
    private boolean completed;

    public void setTodoId(long todoId) {
        this.todoId = this.todoId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTodoOrder(int todoOrder) {
        this.todoOrder = todoOrder;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
