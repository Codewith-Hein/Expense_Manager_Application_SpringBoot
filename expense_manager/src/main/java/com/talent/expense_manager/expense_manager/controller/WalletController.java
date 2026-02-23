package com.talent.expense_manager.expense_manager.controller;


import com.talent.expense_manager.expense_manager.model.Wallet;
import com.talent.expense_manager.expense_manager.request.AddBudgetRequest;
import com.talent.expense_manager.expense_manager.request.WalletRequest;
import com.talent.expense_manager.expense_manager.response.WalletResponse;
import com.talent.expense_manager.expense_manager.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/account/mywallet")
public class WalletController {
    @Autowired
    public final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }


    @GetMapping("/{accountId}")
    public ResponseEntity<WalletResponse> myWallet(@PathVariable String accountId) {

        walletService.myWallet(accountId);
        return ResponseEntity.ok(walletService.myWallet(accountId));
    }

    @PostMapping("/addbudget/{accountId}")
    public ResponseEntity<String> addMyBudget(@PathVariable String accountId, @RequestBody AddBudgetRequest request) {
        walletService.addMyBudget(accountId, request);
        return ResponseEntity.ok("budget add successful");
    }

    @PatchMapping("/updatebudget/{accountId}")
    public ResponseEntity<String> updateBudget(@PathVariable String accountId, @RequestBody AddBudgetRequest request) {
        walletService.updateBudget(accountId, request);
        return ResponseEntity.ok("budget update successful");
    }

    @PatchMapping("/addbaddlance/{walletId}")
    public ResponseEntity<String> addBalance(@PathVariable Long walletId, @RequestBody BigDecimal amount) {
        walletService.addBalance(walletId, amount);
        return ResponseEntity.ok("add balance Successful");

    }

    @PatchMapping("/withdrawbalance/{walletId}")
    public ResponseEntity<String> withDrawBalance(@PathVariable Long walletId, @RequestBody BigDecimal amount) {
        walletService.withDrawBlance(walletId, amount);
        return ResponseEntity.ok("Withdraw Balance Successful");
    }

    @DeleteMapping("/{walletId}")
    public ResponseEntity<String> deleteWallet(@PathVariable Long walletId) {
        walletService.deleteWallet(walletId);
        return ResponseEntity.ok("Wallet delete successful");
    }


    @PostMapping("/createWallet/{accountId}")
    public ResponseEntity<WalletResponse> createWallet(@PathVariable String accountId, @RequestBody WalletRequest request) {
        walletService.createWallet(accountId, request);
        return ResponseEntity.ok(walletService.createWallet(accountId, request));
    }

}
