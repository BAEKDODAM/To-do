package com.practice.todo;

import com.practice.card.entity.Card;
import com.practice.todo.entity.Todo;
import com.practice.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TodoRepositoryTest {
    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void saveTodoTest(){
        Card card = new Card();
        card.setCardName("test");
        card.setTodos(null);

        Todo todo = new Todo();
        todo.setTitle("todo test");
        todo.setCompleted(false);
        todo.setTodoPriority(Todo.TodoPriority.TODO_PRIORITY_FIRST);
        todo.setCard(card);

        Todo savedTodo = todoRepository.save(todo);
        assertNotNull(savedTodo);
        assertTrue(todo.getTitle().equals(savedTodo.getTitle()));
        assertTrue(todo.getTodoPriority().equals(savedTodo.getTodoPriority()));

    }

    @Test
    public void findByIdTest(){
        Card card = new Card();
        card.setCardName("test");
        card.setTodos(null);

        Todo todo = new Todo();
        todo.setTitle("todo test");
        todo.setCompleted(false);
        todo.setTodoPriority(Todo.TodoPriority.TODO_PRIORITY_FIRST);
        todo.setCard(card);

        Todo savedTodo = todoRepository.save(todo);
        Optional<Todo> findTodo = todoRepository.findById(savedTodo.getTodoId());

        assertTrue(findTodo.isPresent());
        assertTrue(findTodo.get().getTitle().equals(todo.getTitle()));
    }
}
