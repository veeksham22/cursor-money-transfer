package com.moneytransfer.service;

import com.moneytransfer.domain.dto.TransferRequest;
import com.moneytransfer.domain.dto.TransferResponse;
import com.moneytransfer.domain.entity.Account;
import com.moneytransfer.domain.entity.TransactionLog;
import com.moneytransfer.domain.enums.AccountStatus;
import com.moneytransfer.domain.enums.TransactionStatus;
import com.moneytransfer.exception.*;
import com.moneytransfer.repository.AccountRepository;
import com.moneytransfer.repository.TransactionLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferService {

    private final AccountRepository accountRepository;
    private final TransactionLogRepository transactionLogRepository;

    @Transactional
    public TransferResponse transfer(TransferRequest request) {
        validateTransfer(request);

        var fromAccount = findActiveAccount(request.getFromAccountId());
        var toAccount = findActiveAccount(request.getToAccountId());

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException(
                    fromAccount.getId(), fromAccount.getBalance(), request.getAmount());
        }

        String idempotencyKey = request.getIdempotencyKey() != null
                ? request.getIdempotencyKey()
                : UUID.randomUUID().toString();

        var existingTransaction = transactionLogRepository.findByIdempotencyKey(idempotencyKey);
        if (existingTransaction.isPresent()) {
            throw new DuplicateTransferException(idempotencyKey);
        }

        fromAccount.debit(request.getAmount());
        toAccount.credit(request.getAmount());

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        var transactionLog = TransactionLog.builder()
                .id("TRX-" + UUID.randomUUID())
                .fromAccountId(fromAccount.getId())
                .toAccountId(toAccount.getId())
                .amount(request.getAmount())
                .status(TransactionStatus.SUCCESS)
                .idempotencyKey(idempotencyKey)
                .build();

        transactionLog = transactionLogRepository.save(transactionLog);

        log.info("Transfer completed: {} from {} to {}", request.getAmount(), fromAccount.getId(), toAccount.getId());

        return TransferResponse.builder()
                .transactionId(transactionLog.getId())
                .status(TransactionStatus.SUCCESS)
                .message("Transfer completed")
                .debitedFrom(fromAccount.getId())
                .creditedTo(toAccount.getId())
                .amount(request.getAmount())
                .build();
    }

    private void validateTransfer(TransferRequest request) {
        if (request.getFromAccountId().equals(request.getToAccountId())) {
            throw new InvalidTransferException("Accounts must be different");
        }
    }

    private Account findActiveAccount(Long accountId) {
        var account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new AccountNotActiveException(accountId, account.getStatus().name());
        }

        return account;
    }
}
