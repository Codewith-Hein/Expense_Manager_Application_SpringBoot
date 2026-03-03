package com.talent.expense_manager.expense_manager.controller;


import com.talent.expense_manager.expense_manager.request.TransactionRequest;
import com.talent.expense_manager.expense_manager.response.*;
import com.talent.expense_manager.expense_manager.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;



    @PostMapping("/account/{accountId}")
    public ResponseEntity<BaseResponse<String>> createTransaction(
            @PathVariable String accountId,
           @Valid @RequestBody TransactionRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Principal: " + auth.getPrincipal());
        System.out.println("Authorities:");
        auth.getAuthorities().forEach(a -> System.out.println(a.getAuthority()));



        SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseUtil.success(
                HttpStatus.OK,
                "TransactionCreate",
                "Post",
                "TransactionCreate Successful",
                transactionService.createTrasaction(accountId, request)
        );

    }



    @GetMapping("/account/{accountId}")
    public BaseResponse<TransactionListResponse> getAllTransactions(
            @PathVariable String accountId) {

        TransactionListResponse response = transactionService.getAllTransactionsByAccountId(accountId);
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
    public ResponseEntity<BaseResponse<String>> updateTransaction(@PathVariable Long transactionId,@Valid @RequestBody TransactionRequest request) {
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
        MonthlySummaryResponse response = transactionService.getMonthlySumary(accountId, year, month);

        return ResponseUtil.success(
                HttpStatus.OK,
                "MonthlySummary",
                "Get",
                "MonthlySummary",
                response
        );

    }



}
