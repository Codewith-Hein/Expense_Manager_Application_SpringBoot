package com.talent.expense_manager.expense_manager.controller;


import com.talent.expense_manager.expense_manager.request.TransactionRequest;
import com.talent.expense_manager.expense_manager.response.*;
import com.talent.expense_manager.expense_manager.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/account/{accountId}")
    public ResponseEntity<BaseResponse<String>> createTransaction(
            @PathVariable String accountId,
            @RequestBody TransactionRequest request) {

        transactionService.createTrasaction(accountId, request);

        return ResponseUtil.success(
                HttpStatus.OK,
                "TransactionCreate",
                "Post",
                "TransactionCreate Successful",
                null
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<TransactionResponse>> getTransactionById(@PathVariable Long id) {
      TransactionResponse response=transactionService.findTransactionById(id);
        return ResponseUtil.success(
                HttpStatus.OK,
                "getTransactionById",
                "Get",
                "FetchTransactionById Successful",
                response
        );
    }

    @GetMapping("/account/{accountId}")
    public BaseResponse<TransactionListResponse> getAllTransactions(
            @PathVariable String accountId) {

         TransactionListResponse response=transactionService.getAllTransactions(accountId);
        return ResponseUtil.success(
                HttpStatus.OK,
                "getAllTransaction",
                "Get",
                "FetchAllTransactionSuccessful",
                response
        ).getBody();


    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<BaseResponse<String>> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);

        return ResponseUtil.success(
                HttpStatus.OK,
                "DeleteTransaction",
                "Delete",
                "deleteTransaction Successful",
                null
        );

    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<BaseResponse<String>> updateTransaction(@PathVariable Long transactionId, @RequestBody TransactionRequest request) {
        transactionService.updateTransaction(transactionId, request);

        return ResponseUtil.success(
                HttpStatus.OK,
                "UpdateTransaction",
                "Put",
                "UpdateTransaction Successful",
                null
        );
    }

    @GetMapping("/summary/{accountId}")
    public ResponseEntity<BaseResponse<MonthlySummaryResponse>> getMonthlySummary(@PathVariable String accountId, @RequestParam int year, @RequestParam int month) {
       MonthlySummaryResponse response=transactionService.getMonthlySumary(accountId, year, month);

        return ResponseUtil.success(
                HttpStatus.OK,
                "MonthlySummary",
                "Get",
                "MonthlySummary",
                response
        );

    }
}
