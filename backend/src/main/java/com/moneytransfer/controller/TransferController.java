package com.moneytransfer.controller;

import com.moneytransfer.domain.dto.TransferRequest;
import com.moneytransfer.domain.dto.TransferResponse;
import com.moneytransfer.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody TransferRequest request) {
        var response = transferService.transfer(request);
        return ResponseEntity.ok(response);
    }
}
