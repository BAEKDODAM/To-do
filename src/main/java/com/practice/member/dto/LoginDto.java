package com.practice.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class LoginDto {
    @Getter
    public static class Post {
        private String username;
        private String password;
    }
    @Getter
    @Setter
    public static class Response {
        private long memberId;
        private String name;
        private String email;
    }

}