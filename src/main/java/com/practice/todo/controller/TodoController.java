package com.practice.todo.controller;

import com.practice.auth.jwt.JwtTokenizer;
import com.practice.member.entity.Member;
import com.practice.response.MultiResponseDto;
import com.practice.response.SingleResponseDto;
import com.practice.todo.dto.TodoPatchDto;
import com.practice.todo.dto.TodoPostDto;
import com.practice.todo.dto.TodoResponseDto;
import com.practice.todo.entity.Todo;
import com.practice.todo.mapper.TodoMapper;
import com.practice.todo.service.TodoService;
import com.practice.utils.UriCreator;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

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
    /*
    @GetMapping("/{todo-id}")
    public ResponseEntity getTodo(@RequestHeader("Authorization") String token,
                                  @PathVariable("todo-id") @Positive long todoId) {
        Todo foundTodo = todoService.findTodo(todoId);
        TodoResponseDto response = mapper.todoToTodoResponseDto(foundTodo);
        return ResponseEntity.ok(new SingleResponseDto<>(response));
    }

    @GetMapping
    public ResponseEntity getTodos(@Positive @RequestParam int page,
                                   @Positive @RequestParam int size) {
        Page<Todo> pageTodo = todoService.findAll(page, size);
        List<Todo> todos = pageTodo.getContent();
        return ResponseEntity.ok(new MultiResponseDto(mapper.todosToTodoResponseDtos(todos), pageTodo));
    }

 */

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
