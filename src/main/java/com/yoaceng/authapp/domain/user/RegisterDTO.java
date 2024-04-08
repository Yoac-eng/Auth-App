package com.yoaceng.authapp.domain.user;

public record RegisterDTO(String login, String password, UserRole role) {
}
