package com.talent.expense_manager.expense_manager.controller;

import com.talent.expense_manager.expense_manager.request.AccountRequest;
import com.talent.expense_manager.expense_manager.request.ChangePasswordRequest;
import com.talent.expense_manager.expense_manager.response.AccountResponse;
import com.talent.expense_manager.expense_manager.response.BaseResponse;
import com.talent.expense_manager.expense_manager.response.ResponseUtil;
import com.talent.expense_manager.expense_manager.service.AccountService;
import com.talent.expense_manager.expense_manager.servicelmpl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    private final AccountService accountService;





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
        LOGGER.info("REST request to update account: {}", accountId);
       AccountResponse response=accountService.updateAccount(accountId,request);
       return ResponseUtil.success(
               HttpStatus.OK,
               "Update Account",
               "Put",
               "Update Account Successful",
               response
       );
    }





    @PatchMapping("/change-password/{accountId}")
    public ResponseEntity<BaseResponse<String>> changePassword(@PathVariable String accountId, @RequestBody ChangePasswordRequest request) {
        LOGGER.info("REST request to change password for account ID: {}", accountId);
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
