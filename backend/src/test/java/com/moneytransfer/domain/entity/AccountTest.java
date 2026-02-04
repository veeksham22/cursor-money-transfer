package com.moneytransfer.domain.entity;

import com.moneytransfer.domain.enums.AccountStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void testDebit_Success() {
        var account = Account.builder()
                .balance(new BigDecimal("1000.00"))
                .status(AccountStatus.ACTIVE)
                .build();

        account.debit(new BigDecimal("300.00"));

        assertEquals(0, new BigDecimal("700.00").compareTo(account.getBalance()));
    }

    @Test
    void testDebit_InsufficientBalance() {
        var account = Account.builder()
                .balance(new BigDecimal("100.00"))
                .status(AccountStatus.ACTIVE)
                .build();

        assertThrows(IllegalStateException.class, () ->
                account.debit(new BigDecimal("200.00")));
    }

    @Test
    void testCredit_Success() {
        var account = Account.builder()
                .balance(new BigDecimal("500.00"))
                .status(AccountStatus.ACTIVE)
                .build();

        account.credit(new BigDecimal("250.00"));

        assertEquals(0, new BigDecimal("750.00").compareTo(account.getBalance()));
    }

    @Test
    void testIsActive_WhenActive() {
        var account = Account.builder().status(AccountStatus.ACTIVE).build();
        assertTrue(account.isActive());
    }

    @Test
    void testIsActive_WhenLocked() {
        var account = Account.builder().status(AccountStatus.LOCKED).build();
        assertFalse(account.isActive());
    }
}
