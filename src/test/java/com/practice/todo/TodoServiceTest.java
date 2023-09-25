package com.practice.todo;

import com.practice.card.entity.Card;
import com.practice.card.service.CardService;
import com.practice.member.entity.Member;
import com.practice.todo.entity.Todo;
import com.practice.todo.repository.TodoRepository;
import com.practice.todo.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

public class TodoServiceTest {
    @Mock
    private TodoRepository todoRepository;
    @Mock
    private CardService cardService;
    //@Spy
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
        Todo mockTodo = new Todo();
        Card mockCard = new Card();
        Member mockMember = new Member();

        mockMember.setMemberId(1L);
        mockCard.setCardId(1L);
        mockTodo.setTodoId(1L);
        mockCard.setMember(mockMember);
        mockTodo.setCard(mockCard);

        when(cardService.findVerifiedCard(1L)).thenReturn(mockCard);

        when(todoRepository.save(any(Todo.class))).thenReturn(mockTodo);
        Todo result = todoService.createTodo(mockTodo,1L, 1L);

        assertEquals(result.getTodoId(), mockTodo.getTodoId());
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    @DisplayName("updateTodo test")
    public void updateTodoTest() {
        long memberId = 1L;
        Todo mockTodo = new Todo();
        Card mockCard = new Card();
        Member mockMember = new Member();

        mockMember.setMemberId(1L);
        mockCard.setCardId(1L);
        mockTodo.setTodoId(1L);
        mockTodo.setTitle("todo");

        mockCard.setMember(mockMember);
        mockTodo.setCard(mockCard);

        given(todoRepository.findById(memberId)).willReturn(Optional.of(mockTodo));

        Todo updateTodo = new Todo();
        updateTodo.setTodoId(1L);
        updateTodo.setTitle("update todo");
        updateTodo.setCard(mockCard);

        given(todoRepository.save(any(Todo.class))).willReturn(updateTodo);

        Todo result = todoService.updateTodo(mockTodo, memberId);
        assertEquals(result.getTitle(), updateTodo.getTitle());
    }

    @Test
    @DisplayName("deleteTodo test")
    public void deleteTodo(){
        Todo mockTodo = new Todo();
        Card mockCard = new Card();
        Member mockMember = new Member();

        mockMember.setMemberId(1L);
        mockCard.setCardId(1L);
        mockTodo.setTodoId(1L);
        mockCard.setMember(mockMember);
        mockTodo.setCard(mockCard);

        given(todoRepository.findById(1L)).willReturn(Optional.of(mockTodo));

        todoService.deleteTodo(mockTodo.getTodoId(), 1L);

        verify(todoRepository, times(1)).delete(mockTodo);
    }
}
