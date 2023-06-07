package com.practice.todo.mapper;

import com.practice.todo.dto.TodoPatchDto;
import com.practice.todo.dto.TodoPostDto;
import com.practice.todo.dto.TodoResponseDto;
import com.practice.todo.entity.Todo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-06T01:26:27+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.18 (Azul Systems, Inc.)"
)
@Component
public class TodoMapperImpl implements TodoMapper {

    @Override
    public Todo todoPostDtoToTodo(TodoPostDto todoPostDto) {
        if ( todoPostDto == null ) {
            return null;
        }

        Todo todo = new Todo();

        todo.setTitle( todoPostDto.getTitle() );
        todo.setTodoOrder( todoPostDto.getTodoOrder() );
        todo.setCompleted( todoPostDto.isCompleted() );

        return todo;
    }

    @Override
    public Todo todoPatchDtoToTodo(TodoPatchDto todoPatchDto) {
        if ( todoPatchDto == null ) {
            return null;
        }

        Todo todo = new Todo();

        todo.setId( todoPatchDto.getId() );
        todo.setTitle( todoPatchDto.getTitle() );
        todo.setTodoOrder( todoPatchDto.getTodoOrder() );
        todo.setCompleted( todoPatchDto.isCompleted() );

        return todo;
    }

    @Override
    public TodoResponseDto todoToTodoResponseDto(Todo todo) {
        if ( todo == null ) {
            return null;
        }

        long id = 0L;
        String title = null;
        int todoOrder = 0;
        boolean completed = false;

        id = todo.getId();
        title = todo.getTitle();
        todoOrder = todo.getTodoOrder();
        completed = todo.isCompleted();

        TodoResponseDto todoResponseDto = new TodoResponseDto( id, title, todoOrder, completed );

        return todoResponseDto;
    }

    @Override
    public List<TodoResponseDto> todosToTodoResponseDtos(List<Todo> todos) {
        if ( todos == null ) {
            return null;
        }

        List<TodoResponseDto> list = new ArrayList<TodoResponseDto>( todos.size() );
        for ( Todo todo : todos ) {
            list.add( todoToTodoResponseDto( todo ) );
        }

        return list;
    }
}
