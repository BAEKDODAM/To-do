package com.practice.todo.service;

import com.practice.card.entity.Card;
import com.practice.card.service.CardService;
import com.practice.exception.BusinessLogicException;
import com.practice.exception.ExceptionCode;
import com.practice.todo.entity.Todo;
import com.practice.todo.repository.TodoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final CardService cardService;

    public TodoService(TodoRepository todoRepository, CardService cardService) {
        this.todoRepository = todoRepository;
        this.cardService = cardService;
    }

    public Todo createTodo(Todo todo, long cardId) {
        Card card = cardService.findVerifiedCard(cardId);
        todo.setCard(card);
        todo.setCompleted(false);
        return todoRepository.save(todo);
    }
    public Todo updateTodo(Todo todo) {
        // 존재하는 todo인지 검증
        Todo findTodo = findVerifiedTodo(todo.getTodoId());

        // title 정보와 completed 정보 업데이트
        Optional.ofNullable(todo.getTitle())
                .ifPresent(title -> findTodo.setTitle(title));
        Optional.ofNullable(todo.isCompleted())
                .ifPresent(completed -> findTodo.setCompleted(completed));
        Optional.ofNullable(todo.getTodoPriority())
                .ifPresent(todoPriority -> findTodo.setTodoPriority(todoPriority));
        // todo 정보 업데이트
        return todoRepository.save(findTodo);
    }
/*
    @Transactional(readOnly = true)
    public Todo findTodo(long todoId) {
        return findVerifiedTodo(todoId);
    }

    public List<Todo> findTodos() {
        return (List<Todo>) todoRepository.findAll();
    }
    public Page<Todo> findAll(int page, int size){
        return todoRepository.findAll(PageRequest.of(page - 1, size, Sort.by("todoPriority").descending()));
    }
 */

    public void deleteTodo(long todoId, long memberId) {
        Todo findTodo = findVerifiedTodo(todoId);
        validateTodoOwnership(findTodo, memberId);
        // if(card.getMember().getMemberId() == memberId)
        //cardService.findCardsByMemberId(memberId);
        todoRepository.delete(findTodo);

    }

    // 존재하는 todo 목록인지 검증
    @Transactional(readOnly = true)
    public Todo findVerifiedTodo(long todoId) {
        return todoRepository
                .findById(todoId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.TODO_NOT_FOUND));
    }

    public long validateTodoOwnership(Todo todo, long memberId){
        Card card = todo.getCard();
        if(card.getMember().getMemberId() != memberId){
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }
    }
}
