package com.practice.todo.controller.v2;

import com.practice.todo.dto.TodoPatchDto;
import com.practice.todo.dto.TodoPostDto;
import com.practice.todo.dto.TodoResponseDto;
import com.practice.todo.entity.Todo;
import com.practice.todo.mapper.TodoMapper;
import com.practice.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/v2/todos")
@Validated
public class TodoControllerV2 {
    private final TodoService  todoService;
    private final TodoMapper mapper;

    public TodoControllerV2(TodoService todoService, TodoMapper mapper) {
        this.todoService = todoService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postTodo(@Valid @RequestBody TodoPostDto todoPostDto) {
        Todo todo = mapper.todoPostDtoToTodo(todoPostDto);
        Todo response = todoService.createTodo(todo);

        return new ResponseEntity<>(mapper.todoToTodoResponseDto(response),
                HttpStatus.CREATED);
    }

    @PatchMapping("/{todo-id}")
    public ResponseEntity patchTodo(@PathVariable("todo-id") @Positive long todoId,
                                    @Valid @RequestBody TodoPatchDto todoPatchDto) {
        todoPatchDto.setTodoId(todoId);
        Todo todo = mapper.todoPatchDtoToTodo(todoPatchDto);
        Todo response = todoService.updateTodo(todo);

        return new ResponseEntity<>(mapper.todoToTodoResponseDto(response),
                HttpStatus.OK);
    }

    @GetMapping("/{todo-id}")
    public ResponseEntity getTodo(@PathVariable("todo-id") @Positive long todoId) {
        Todo response = todoService.findTodo(todoId);
        return new ResponseEntity<>(mapper.todoToTodoResponseDto(response),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getTodos() {
        List<Todo> todos = todoService.findTodos();
        List<TodoResponseDto> response =
                todos.stream()
                        .map(todo -> mapper.todoToTodoResponseDto(todo))
                        .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{todo-id}")
    public ResponseEntity deleteTodo(@PathVariable("todo-id") @Positive long todoId)  {
        System.out.println("# delete");
        todoService.deleteTodo(todoId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity deleteAllTodos() {
        todoService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
