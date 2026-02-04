package com.moneytransfer.domain.dto;

import com.moneytransfer.domain.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse {

    private String transactionId;
    private TransactionStatus status;
    private String message;
    private Long debitedFrom;
    private Long creditedTo;
    private BigDecimal amount;
}
