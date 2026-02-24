package com.talent.expense_manager.expense_manager.controller;

import com.talent.expense_manager.expense_manager.request.AccountRequest;
import com.talent.expense_manager.expense_manager.request.ChangePasswordRequest;
import com.talent.expense_manager.expense_manager.response.AccountResponse;
import com.talent.expense_manager.expense_manager.response.BaseResponse;
import com.talent.expense_manager.expense_manager.response.ResponseUtil;
import com.talent.expense_manager.expense_manager.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {


    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<AccountResponse>> createAccount(@RequestBody AccountRequest request) {
        AccountResponse account = accountService.createAccount(request);

        return ResponseUtil.success(
                HttpStatus.OK,
                "Register_Account",
                "Post",
                "Account Create Successful",
                account
        );

    }


    @GetMapping("/login")
    public ResponseEntity<BaseResponse<AccountResponse>> login(@RequestBody AccountRequest request) {

      AccountResponse account=accountService.login(request);

        return ResponseUtil.success(
                HttpStatus.OK,
                "Login",
                "Get",
                "Login Account Successful",
                account

        );
    }

    @PostMapping("/logout/{accountId}")
    public ResponseEntity<BaseResponse<String>> logout(@PathVariable String accountId) {

        accountService.logout(accountId);
        return ResponseUtil.success(
                HttpStatus.OK,
                "Logout",
                "Post",
                "Logout Successful",
                null

        );

    }

    @PutMapping("/updateAccount/{accountId}")
    public ResponseEntity<BaseResponse<AccountResponse>> updateAccount(@PathVariable String accountId, @RequestBody AccountRequest request) {

       AccountResponse response=accountService.updateAccount(accountId,request);
       return ResponseUtil.success(
               HttpStatus.OK,
               "Update Account",
               "Put",
               "Update Account Successful",
               response
       );
    }


    @GetMapping
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

    @DeleteMapping("/{accountId}")
    public ResponseEntity<BaseResponse<String>> accountDelete(@PathVariable String accountId) {
        accountService.accountDelete(accountId);
        return ResponseUtil.success(
                HttpStatus.OK,
                "delectAccount",
                "Get",
                "DeleteAccountSuccessful" ,
                null
        );
    }

    @PatchMapping("/change-password/{accountId}")
    public ResponseEntity<BaseResponse<String>> changePassword(@PathVariable String accountId, @RequestBody ChangePasswordRequest request) {

            accountService.changePassword(accountId, request);

            return ResponseUtil.success(
                    HttpStatus.OK,
                    "change_password",
                    "PATCH",
                    "Change Password Successful",
                    null
            );



    }


}
