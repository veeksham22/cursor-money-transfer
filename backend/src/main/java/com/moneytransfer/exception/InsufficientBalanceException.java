package com.moneytransfer.exception;

import java.math.BigDecimal;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException(Long accountId, BigDecimal balance, BigDecimal amount) {
        super("Insufficient balance in account " + accountId + ": available=" + balance + ", required=" + amount);
    }
}
