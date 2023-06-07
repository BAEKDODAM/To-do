package com.practice.todo.mapper;

import com.practice.todo.dto.TodoPatchDto;
import com.practice.todo.dto.TodoPostDto;
import com.practice.todo.dto.TodoResponseDto;
import com.practice.todo.entity.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TodoMapper {
    Todo todoPostDtoToTodo(TodoPostDto todoPostDto);
    Todo todoPatchDtoToTodo(TodoPatchDto todoPatchDto);
    TodoResponseDto todoToTodoResponseDto(Todo todo);
    List<TodoResponseDto> todosToTodoResponseDtos(List<Todo> todos);
}
