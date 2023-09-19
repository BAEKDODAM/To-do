package com.practice.card.tdo;

import com.practice.todo.dto.TodoResponseDto;
import com.practice.todo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CardDto {
    @Getter
    //@AllArgsConstructor
    @NoArgsConstructor
    public static class Post {
        private String cardName;

    }
    public static class Patch {}

    @Getter
    @Builder
    public static class Response{
        private String cardName;
        private long cardId;
        private List<TodoResponseDto> todos;

        public List<TodoResponseDto> getTodos() {
            return todos;
        }
    }
}
