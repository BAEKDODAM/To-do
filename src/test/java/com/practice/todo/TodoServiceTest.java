package com.practice.todo;

import com.practice.card.entity.Card;
import com.practice.card.service.CardService;
import com.practice.todo.entity.Todo;
import com.practice.todo.repository.TodoRepository;
import com.practice.todo.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

public class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;
    @Mock
    private CardService cardService;
    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        todoService = new TodoService(todoRepository, cardService);
    }

    @Test
    @DisplayName("createTodo test")
    public void createTodoTest() {
        Card mockCard = new Card();
        when(cardService.findVerifiedCard(1L)).thenReturn(mockCard);

        Todo mockTodo = new Todo();
        mockTodo.setTodoId(1L);

        when(todoRepository.save(any(Todo.class))).thenReturn(mockTodo);
        Todo result = todoService.createTodo(mockTodo,1L);

        assertEquals(result.getTodoId(), mockTodo.getTodoId());
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    @DisplayName("updateTodo test")
    public void updateTodoTest() {
        Todo mockTodo = new Todo();
        mockTodo.setTodoId(1L);
        mockTodo.setTitle("todo");
        when(todoRepository.findById(1L)).thenReturn(Optional.of(mockTodo));

        Todo updateTodo = new Todo();
        updateTodo.setTodoId(1L);
        updateTodo.setTitle("update todo");
        when(todoRepository.save(any(Todo.class))).thenReturn(updateTodo);

        Todo result = todoService.updateTodo(mockTodo);
        assertEquals(result.getTitle(), updateTodo.getTitle());
    }

    @Test
    @DisplayName("deleteTodo test")
    public void deleteTodo(){
        Todo mockTodo = new Todo();
        mockTodo.setTodoId(1L);
        when(todoRepository.findById(1L)).thenReturn(Optional.of(mockTodo));
        todoService.deleteTodo(mockTodo.getTodoId());

        verify(todoRepository, times(1)).delete(mockTodo);
    }
}
