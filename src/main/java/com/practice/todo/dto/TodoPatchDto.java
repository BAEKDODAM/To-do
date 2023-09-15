package com.practice.todo.dto;

import com.practice.todo.entity.Todo;
import lombok.Getter;

@Getter
public class TodoPatchDto {
    private long todoId;
    private String title;
    //private int todoOrder;
    private boolean completed;
    private Todo.TodoPriority todoPriority;

    public void setTodoId(long todoId) {
        this.todoId = todoId;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setTodoPriority(Todo.TodoPriority todoPriority) {
        this.todoPriority = todoPriority;
    }
}
