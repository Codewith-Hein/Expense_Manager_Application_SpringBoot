package com.talent.expense_manager.expense_manager.controller;

import com.talent.expense_manager.expense_manager.request.AccountRequest;
import com.talent.expense_manager.expense_manager.request.ChangePasswordRequest;
import com.talent.expense_manager.expense_manager.response.AccountResponse;
import com.talent.expense_manager.expense_manager.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {


    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest request) {
        return ResponseEntity.ok(accountService.createAccount(request));
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AccountRequest request) {
        boolean success = accountService.login(request.getEmail(), request.getPassword());
        if (success) return ResponseEntity.ok("Login Successful");
        else return ResponseEntity.status(404).body("Invalid credentials");
    }

    @PostMapping("/logout/{accountId}")
    public ResponseEntity<String> logout(@PathVariable String accountId) {

        accountService.logout(accountId);
        return ResponseEntity.ok("Logged out successfully");

    }

    @PutMapping("/updateAccount/{accountId}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable String accountId, @RequestBody AccountRequest request) {

       return ResponseEntity.ok(accountService.updateAccount(accountId,request));
    }


    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        List<AccountResponse> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> accountDelete(@PathVariable String accountId) {
        accountService.accountDelete(accountId);
        return ResponseEntity.ok("Account deleted successfully");
    }

    @PatchMapping("/change-password/{accountId}")
    public ResponseEntity<String> changePassword(@PathVariable String accountId, @RequestBody ChangePasswordRequest request){
        accountService.changePassword(accountId,request);
        return ResponseEntity.ok("Password update successfully");
    }


}
