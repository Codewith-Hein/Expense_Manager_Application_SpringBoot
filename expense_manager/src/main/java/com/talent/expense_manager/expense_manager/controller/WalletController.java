package com.talent.expense_manager.expense_manager.controller;


import com.talent.expense_manager.expense_manager.request.AddBudgetRequest;
import com.talent.expense_manager.expense_manager.response.WalletResponse;
import com.talent.expense_manager.expense_manager.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account/mywallet")
public class WalletController {
    @Autowired
    public final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }


    @GetMapping("/{accountId}")
    public ResponseEntity<WalletResponse> myWallet(@PathVariable String accountId){

        walletService.myWallet(accountId);
        return ResponseEntity.ok(walletService.myWallet(accountId));

    }

    @PostMapping("/addbudget/{accountId}")
    public ResponseEntity<String> addMyBudget(@PathVariable String accountId, @RequestBody AddBudgetRequest request){
        walletService.addMyBudget(accountId,request);
        return ResponseEntity.ok("budget add successful");
    }


}
