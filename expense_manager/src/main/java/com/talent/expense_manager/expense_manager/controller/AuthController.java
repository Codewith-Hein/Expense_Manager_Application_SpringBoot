package com.talent.expense_manager.expense_manager.controller;

import com.talent.expense_manager.expense_manager.model.Account;
import com.talent.expense_manager.expense_manager.repository.AccountRepository;
import com.talent.expense_manager.expense_manager.request.AccountRequest;
import com.talent.expense_manager.expense_manager.request.LoginRequest;
import com.talent.expense_manager.expense_manager.request.RefreshTokenRequest;
import com.talent.expense_manager.expense_manager.response.AccountResponse;
import com.talent.expense_manager.expense_manager.response.BaseResponse;
import com.talent.expense_manager.expense_manager.response.ResponseUtil;
import com.talent.expense_manager.expense_manager.response.TokenResponseDto;
import com.talent.expense_manager.expense_manager.security.JWTService;
import com.talent.expense_manager.expense_manager.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final AccountRepository accountRepository;

    private final JWTService jwtService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AccountResponse>> login(@Valid @RequestBody LoginRequest request) {
        LOGGER.info("REST request to login account: {}", request.getEmail());
        AccountResponse account = authService.login(request);

        return ResponseUtil.success(
                HttpStatus.OK,
                "Login",
                "Get",
                "Login Account Successful",
                account

        );
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<AccountResponse>> createAccount(@Valid @RequestBody AccountRequest request) {

        LOGGER.info("REST request to register account for email: {}", request.getEmail());

        AccountResponse accountResponse = authService.createAccount(request);

        return ResponseUtil.success(
                HttpStatus.OK,
                "Login",
                "Get",
                "Account Register Successful",
                accountResponse

        );


    }

    @PostMapping("/refresh-token")
    ResponseEntity<BaseResponse<TokenResponseDto>> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        TokenResponseDto responseDto = authService.refreshToken(request);
        LOGGER.info("REST request to refresh token for account:");
        return ResponseUtil.success(
                HttpStatus.OK,
                "Login",
                "Get",
                "Account Register Successful",
                responseDto

        );
    }


}
