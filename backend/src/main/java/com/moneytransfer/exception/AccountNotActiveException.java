package com.moneytransfer.exception;

public class AccountNotActiveException extends RuntimeException {

    public AccountNotActiveException(Long accountId, String status) {
        super("Account " + accountId + " is not active (status: " + status + ")");
    }
}
