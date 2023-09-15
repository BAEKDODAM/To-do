package com.practice.todo.controller;

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
    private final static String TODO_DEFAULT_URL = "/v1/todos";
    private final TodoService todoService;
    private final TodoMapper mapper;

    public TodoController(TodoService todoService, TodoMapper mapper) {
        this.todoService = todoService;
        this.mapper = mapper;
    }

    // todo 생성, todo 수정(우선순위 수정, 완료 표시, 목록 중 하나 삭제), todo 전체 삭제
    @PostMapping
    public ResponseEntity postTodo(@Valid @RequestBody TodoPostDto requestBody) {
        Todo createdTodo = todoService.createTodo(mapper.todoPostDtoToTodo(requestBody));
        URI location = UriCreator.createUri(TODO_DEFAULT_URL, createdTodo.getTodoId());
        return ResponseEntity.created(location).build();
        //return ResponseEntity.created(new SingleResponseDto<>(response));
    }

    @PatchMapping("/{todo-id}")
    public ResponseEntity patchTodo(@PathVariable("todo-id")  @Positive long todoId,
                                    @Valid @RequestBody TodoPatchDto requestBody) {
        requestBody.setTodoId(todoId);
        Todo updateTodo = todoService.updateTodo(mapper.todoPatchDtoToTodo(requestBody));
        return ResponseEntity.ok(mapper.todoToTodoResponseDto(updateTodo));
    }
    @GetMapping("/{todo-id}")
    public ResponseEntity getTodo(@PathVariable("todo-id") @Positive long todoId) {
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

    @DeleteMapping("/{todo-id}")
    public ResponseEntity deleteTodo(@PathVariable("todo-id") long todoId) {
        todoService.deleteTodo(todoId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping
    public ResponseEntity deleteAllTodos() {
        todoService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
