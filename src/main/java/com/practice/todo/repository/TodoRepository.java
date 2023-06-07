package com.practice.todo.repository;

import com.practice.todo.entity.Todo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TodoRepository extends CrudRepository<Todo, Long> {
    @Override
    Optional<Todo> findById(Long id);
}
