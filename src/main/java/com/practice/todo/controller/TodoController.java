package com.practice.todo.controller;

import com.practice.auth.jwt.JwtTokenizer;
import com.practice.todo.dto.TodoPatchDto;
import com.practice.todo.dto.TodoPostDto;
import com.practice.todo.entity.Todo;
import com.practice.todo.mapper.TodoMapper;
import com.practice.todo.service.TodoService;
import com.practice.utils.UriCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@RequestMapping("/todo")
@Validated
public class TodoController {
    private final static String TODO_DEFAULT_URL = "/todo";
    private final TodoService todoService;
    private final TodoMapper mapper;
    private final JwtTokenizer jwtTokenizer;

    public TodoController(TodoService todoService, TodoMapper mapper, JwtTokenizer jwtTokenizer) {
        this.todoService = todoService;
        this.mapper = mapper;
        this.jwtTokenizer = jwtTokenizer;
    }

    @PostMapping("/{cardId}")
    public ResponseEntity postTodo(@RequestHeader("Authorization") String token,
                                   @PathVariable("cardId") @Positive long cardId,
                                   @Valid @RequestBody TodoPostDto requestBody) {
        long memberId = jwtTokenizer.getMemberId(token);
        Todo createdTodo = todoService.createTodo(mapper.todoPostDtoToTodo(requestBody), cardId, memberId);
        URI location = UriCreator.createUri(TODO_DEFAULT_URL, createdTodo.getTodoId());
        return ResponseEntity.created(location).build();
        //return ResponseEntity.created(new SingleResponseDto<>(response));
    }

    @PatchMapping("/{todo-id}")
    public ResponseEntity patchTodo(@RequestHeader("Authorization") String token,
                                    @PathVariable("todo-id")  @Positive long todoId,
                                    @Valid @RequestBody TodoPatchDto requestBody) {
        long memberId = jwtTokenizer.getMemberId(token);
        requestBody.setTodoId(todoId);
        Todo updateTodo = todoService.updateTodo(mapper.todoPatchDtoToTodo(requestBody), memberId);
        return ResponseEntity.ok(mapper.todoToTodoResponseDto(updateTodo));
    }

    @DeleteMapping("/{todo-id}")
    public ResponseEntity deleteTodo(@RequestHeader("Authorization") String token,
                                     @PathVariable("todo-id") long todoId) {
        long memberId = jwtTokenizer.getMemberId(token);
        todoService.deleteTodo(todoId, memberId);
        return ResponseEntity.noContent().build();
    }
    /*
    @DeleteMapping
    public ResponseEntity deleteAllTodos(@RequestHeader("Authorization") String token) {
        todoService.deleteAll();
        return ResponseEntity.noContent().build();
    }

     */
}
