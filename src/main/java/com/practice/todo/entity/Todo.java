package com.practice.todo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

//import javax.persistence.Entity;
//import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("TODOS")
public class Todo {
    @Id
    private long id;
    private String title;
    private int todoOrder;
    private boolean completed;
}
