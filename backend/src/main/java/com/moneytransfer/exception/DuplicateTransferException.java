package com.moneytransfer.exception;

public class DuplicateTransferException extends RuntimeException {

    public DuplicateTransferException(String idempotencyKey) {
        super("Duplicate transfer with idempotency key: " + idempotencyKey);
    }
}
