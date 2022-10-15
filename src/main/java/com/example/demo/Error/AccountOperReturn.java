package com.example.demo.Error;

public class AccountOperReturn {
    private int code;
    private String description;

    public AccountOperReturn(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
