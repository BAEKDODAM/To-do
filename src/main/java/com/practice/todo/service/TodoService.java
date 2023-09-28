package com.practice.todo.service;

import com.practice.card.entity.Card;
import com.practice.card.service.CardService;
import com.practice.exception.BusinessLogicException;
import com.practice.exception.ExceptionCode;
import com.practice.todo.entity.Todo;
import com.practice.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Todo createTodo(Todo todo, long cardId, long memberId) {
        Card card = cardService.findVerifiedCard(cardId);
        todo.setCard(card);
        todo.setCompleted(false);
        validateTodoOwnership(todo, memberId);
        return todoRepository.save(todo);
    }
    public Todo updateTodo(Todo todo, long memberId) {
        // 존재하는 todo인지 검증
        Todo findTodo = findVerifiedTodo(todo.getTodoId());
        // todo 작성자와 memberId가 일지하는지 검증
        validateTodoOwnership(findTodo, memberId);
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

    public void deleteTodo(long todoId, long memberId) {
        Todo findTodo = findVerifiedTodo(todoId);
        validateTodoOwnership(findTodo, memberId);
        todoRepository.delete(findTodo);

    }

    // 존재하는 todo 목록인지 검증
    @Transactional(readOnly = true)
    public Todo findVerifiedTodo(long todoId) {
        return todoRepository
                .findById(todoId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.TODO_NOT_FOUND));
    }

    public void validateTodoOwnership(Todo todo, long memberId){
        Card card = todo.getCard();
        if(card.getMember().getMemberId() != memberId){
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }
    }
}
