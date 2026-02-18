package com.talent.expense_manager.expense_manager.controller;


import com.talent.expense_manager.expense_manager.request.TransactionRequest;
import com.talent.expense_manager.expense_manager.response.MonthlySummaryResponse;
import com.talent.expense_manager.expense_manager.response.TransactionListResponse;
import com.talent.expense_manager.expense_manager.response.TransactionResponse;
import com.talent.expense_manager.expense_manager.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/account/{accountId}")
    public ResponseEntity<String> createTransaction(
            @PathVariable String accountId,
            @RequestBody TransactionRequest request) {

        transactionService.createTrasaction(accountId, request);

        return ResponseEntity.ok("Transaction created successfully");

    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.findTransactionById(id));
    }

    @GetMapping("/account/{accountId}")
    public TransactionListResponse getAllTransactions(
            @PathVariable String accountId) {

        return transactionService.getAllTransactions(accountId);

    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
        return ResponseEntity.ok("Transaction delete Successfull");
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<String> updateTransaction(@PathVariable Long transactionId, @RequestBody TransactionRequest request) {
        transactionService.updateTransaction(transactionId, request);
        return ResponseEntity.ok("updateTransactionSuccessful");
    }

    @GetMapping("/summary/{accountId}")
    public ResponseEntity<MonthlySummaryResponse> getMonthlySummary(@PathVariable String accountId, @RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(transactionService.getMonthlySumary(accountId, year, month));
    }
}
