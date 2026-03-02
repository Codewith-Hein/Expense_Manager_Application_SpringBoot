package com.talent.expense_manager.expense_manager.exception;

public class AccountNotFound extends RuntimeException {
    public AccountNotFound(String message) {
        super(message);
    }
}
