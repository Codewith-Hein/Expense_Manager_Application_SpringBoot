package com.talent.expense_manager.expense_manager.controller;


import com.talent.expense_manager.expense_manager.model.Wallet;
import com.talent.expense_manager.expense_manager.request.AddBudgetRequest;
import com.talent.expense_manager.expense_manager.request.WalletRequest;
import com.talent.expense_manager.expense_manager.response.BaseResponse;
import com.talent.expense_manager.expense_manager.response.ResponseUtil;
import com.talent.expense_manager.expense_manager.response.WalletResponse;
import com.talent.expense_manager.expense_manager.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.json.JsonMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/account/mywallet")
public class WalletController {
    @Autowired
    public final WalletService walletService;
    @Autowired
    private JsonMapper.Builder builder;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }


    @GetMapping("/{accountId}")
    public ResponseEntity<BaseResponse<WalletResponse>> myWallet(@PathVariable String accountId) {

      WalletResponse response=  walletService.myWallet(accountId);
        return ResponseUtil.success(
                HttpStatus.OK,
                "myWallet",
                "Get",
                "FetchMyWallet Successful",
                response
        );
    }

    @PostMapping("/addbudget/{accountId}")
    public ResponseEntity<BaseResponse<String>> addMyBudget(@PathVariable String accountId, @RequestBody AddBudgetRequest request) {
        walletService.addMyBudget(accountId, request);
        return ResponseUtil.success(
                HttpStatus.OK,
                "addBudget",
                "Post",
                "AddBudget Successful",
                null
        );
    }

    @PatchMapping("/updatebudget/{accountId}")
    public ResponseEntity<BaseResponse<String>> updateBudget(@PathVariable String accountId, @RequestBody AddBudgetRequest request) {
        walletService.updateBudget(accountId, request);
        return ResponseUtil.success(
                HttpStatus.OK,
                "updateBudget",
                "Patch",
                "updateBudget Successful",
                null
        );
    }

    @PatchMapping("/addbaddlance/{walletId}")
    public ResponseEntity<BaseResponse<String>> addBalance(@PathVariable Long walletId, @RequestBody BigDecimal amount) {
        walletService.addBalance(walletId, amount);

        return ResponseUtil.success(
                HttpStatus.OK,
                "addBalance",
                "Patch",
                "AddBalance Successful",
                null
        );

    }

    @PatchMapping("/withdrawbalance/{walletId}")
    public ResponseEntity<BaseResponse<String>> withDrawBalance(@PathVariable Long walletId, @RequestBody BigDecimal amount) {
        walletService.withDrawBlance(walletId, amount);
        return ResponseUtil.success(
                HttpStatus.OK,
                "withDrawBalance",
                "Patch",
                "WithDrawBalance Successful",
                null
        );
    }

    @DeleteMapping("/{walletId}")
    public ResponseEntity<BaseResponse<String>> deleteWallet(@PathVariable Long walletId) {
        walletService.deleteWallet(walletId);
        return ResponseUtil.success(
                HttpStatus.OK,
                "deleteWallet",
                "Delete",
                "WithDrawBalance Successful",
                null
        );
    }


    @PostMapping("/createWallet/{accountId}")
    public ResponseEntity<BaseResponse<WalletResponse>> createWallet(@PathVariable String accountId, @RequestBody WalletRequest request) {
       WalletResponse wallet= walletService.createWallet(accountId, request);

        return ResponseUtil.success(
                HttpStatus.OK,
                "createWallet",
                "Post",
                "CreateWallet Successful",
                wallet
        );

    }

}
