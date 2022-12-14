package com.example.demo.Entity;

import org.springframework.lang.Nullable;

public class AccountEntity {
    private String username;
    private String password;
    private String email;

    public AccountEntity(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

}
