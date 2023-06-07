package com.practice.todo.service;

import com.practice.exception.BusinessLogicException;
import com.practice.exception.ExceptionCode;
import com.practice.todo.entity.Todo;
import com.practice.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo createTodo(Todo todo) {
        todo.setCompleted(false);
        return todoRepository.save(todo);
    }
    public Todo updateTodo(Todo todo) {
        // 존재하는 todo인지 검증
        Todo findTodo = findVerifiedTodo(todo.getId());


        // title 정보와 completed 정보 업데이트
        Optional.ofNullable(todo.getTitle())
                .ifPresent(title -> findTodo.setTitle(title));
        Optional.ofNullable(todo.isCompleted())
                .ifPresent(completed -> findTodo.setCompleted(completed));

        // todo 정보 업데이트
        return todoRepository.save(findTodo);
    }

    public Todo findTodo(long id) {
        return findVerifiedTodo(id);
    }

    public List<Todo> findTodos() {
        return (List<Todo>) todoRepository.findAll();

    }

    public void deleteTodo(long id) {
        Todo findTodo = findVerifiedTodo(id);
        todoRepository.delete(findTodo);
    }

    // 존재하는 todo 목록인지 검증
    public Todo findVerifiedTodo(long id){
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        Todo findTodo = optionalTodo.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.TODO_NOT_FOUND));
        return findTodo;
    }
}
