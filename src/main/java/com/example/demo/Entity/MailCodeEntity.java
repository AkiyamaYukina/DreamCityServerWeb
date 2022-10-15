package com.example.demo.Entity;


public class MailCodeEntity {
    private AccountEntity account;
    private String code;
    private long timestamp;

    public MailCodeEntity(AccountEntity account, String code, long timestamp) {
        this.account = account;
        this.code = code;
        this.timestamp = timestamp;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public String getCode() {
        return code;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
