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
    @Test
    public void testDeleteTodo() {
        // 가상의 todoId와 memberId 값을 설정
        long todoId = 1L;
        long memberId = 123L;

        // 테스트용 Todo 객체 생성
        Todo todo = new Todo();
        todo.setTodoId(todoId);

        // todoRepository.findById(todoId) 메서드가 호출될 때 가상의 Todo 객체 반환
        when(todoRepository.findById(todoId)).thenReturn(Optional.of(todo));

        doNothing().when(todoService).validateTodoOwnership(todo, memberId);

        // 예외가 발생하지 않는지 확인
        assertDoesNotThrow(() -> {
            todoService.deleteTodo(todoId, memberId);
        });

        // deleteTodo 메서드 호출
        todoService.deleteTodo(todoId, memberId);

        // validateTodoOwnership 메서드에서 memberId와 Todo 객체의 소유자 확인
        //verify(todoService).validateTodoOwnership(todo, memberId);

        // todoRepository.delete 메서드가 호출되는지 확인
        verify(todoRepository).delete(todo);
    }

}
