package com.practice.todo.repository;

import com.practice.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long>{//extends CrudRepository<Todo, Long> {
}
