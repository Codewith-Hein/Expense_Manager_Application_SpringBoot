package com.talent.expense_manager.expense_manager.controller;


import com.talent.expense_manager.expense_manager.model.Wallet;
import com.talent.expense_manager.expense_manager.repository.WalletRepository;
import com.talent.expense_manager.expense_manager.response.*;
import com.talent.expense_manager.expense_manager.service.AccountService;
import com.talent.expense_manager.expense_manager.service.TransactionService;
import com.talent.expense_manager.expense_manager.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final WalletService walletService;

    @GetMapping("/getallaccounts")
    public ResponseEntity<BaseResponse<List<AccountResponse>>> getAllAccounts() {
        List<AccountResponse> accounts = accountService.getAllAccounts();


        return ResponseUtil.success(
                HttpStatus.OK,
                "FetchUserList",
                "Get",
                "Fetching UserList Successful",
                accounts
        );
    }


    @DeleteMapping("accountdelete/{accountId}")
    public ResponseEntity<BaseResponse<String>> accountDelete(@PathVariable String accountId) {
        accountService.accountDelete(accountId);
        return ResponseUtil.success(
                HttpStatus.OK,
                "delectAccount",
                "Get",
                "DeleteAccountSuccessful",
                null
        );
    }


    @GetMapping("/viewalltransactions")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<List<TransactionResponse>>> viewAllTransaction() {








        return ResponseUtil.success(
                HttpStatus.OK,
                "View-Get",
                "Get",
                "View All Transactions",
                transactionService.viewAllTransaction()
        );

    }

    @GetMapping("/getallwallets")
    public ResponseEntity<BaseResponse<List<WalletResponse>>> getAllWallet() {


        return ResponseUtil.success(
                HttpStatus.OK,
                "View-Get",
                "Get",
                "View All Wallet",
                walletService.getAllWallet()
        );

    }


}
