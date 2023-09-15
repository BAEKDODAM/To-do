package com.practice.todo.entity;

import com.practice.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.lang.reflect.Member;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Todo extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long todoId;

    @Column(nullable = false)
    private String title;

    //@Column(nullable = false)
    //private int todoOrder;

    @Column(nullable = false)
    private boolean completed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TodoPriority todoPriority;
/*
    public Todo(String title) {
        this.title = title;
    }

    public Todo(String title, int todoOrder, boolean completed) {
        this.title = title;
        this.todoOrder = todoOrder;
        this.completed = completed;
    }

 */

    public enum TodoPriority {
        TODO_PRIORITY_FIRST("1순위"),
        TODO_PRIORITY_SECOND("2순위"),
        TODO_PRIORITY_THIRD("3순위");

        @Getter
        private String priority;

        TodoPriority(String priority) {
            this.priority = priority;
        }
    }
}
