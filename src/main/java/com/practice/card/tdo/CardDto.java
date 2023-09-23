package com.practice.card.tdo;

import com.practice.todo.dto.TodoResponseDto;
import com.practice.todo.entity.Todo;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

public class CardDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {
        private String cardName;

    }
    public static class Patch {}

    @Getter
    @Builder
    //@NoArgsConstructor
    public static class Response{
        private String cardName;
        private long cardId;
        private List<TodoResponseDto> todos;

        public List<TodoResponseDto> getTodos() {
            return todos;
        }

        public Response(String cardName, long cardId, List<TodoResponseDto> todos) {
            this.cardName = cardName;
            this.cardId = cardId;
            this.todos = todos;
        }

        public Response(long cardId) {
            this.cardId = cardId;
        }
    }
}
