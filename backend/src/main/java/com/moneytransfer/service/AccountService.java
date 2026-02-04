package com.moneytransfer.service;

import com.moneytransfer.domain.dto.AccountResponse;
import com.moneytransfer.domain.dto.TransactionResponse;
import com.moneytransfer.domain.entity.Account;
import com.moneytransfer.domain.entity.TransactionLog;
import com.moneytransfer.domain.enums.TransactionStatus;
import com.moneytransfer.exception.AccountNotFoundException;
import com.moneytransfer.repository.AccountRepository;
import com.moneytransfer.repository.TransactionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionLogRepository transactionLogRepository;

    public AccountResponse getAccount(Long id) {
        var account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        return mapToResponse(account);
    }

    public BigDecimal getBalance(Long id) {
        var account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        return account.getBalance();
    }

    public List<TransactionResponse> getTransactions(Long accountId) {
        accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        return transactionLogRepository.findByAccountId(accountId)
                .stream()
                .map(this::mapToTransactionResponse)
                .collect(Collectors.toList());
    }

    private AccountResponse mapToResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .holderName(account.getHolderName())
                .balance(account.getBalance())
                .status(account.getStatus())
                .build();
    }

    private TransactionResponse mapToTransactionResponse(TransactionLog log) {
        return TransactionResponse.builder()
                .id(log.getId())
                .fromAccountId(log.getFromAccountId())
                .toAccountId(log.getToAccountId())
                .amount(log.getAmount())
                .status(log.getStatus())
                .failureReason(log.getFailureReason())
                .createdOn(log.getCreatedOn())
                .build();
    }
}
