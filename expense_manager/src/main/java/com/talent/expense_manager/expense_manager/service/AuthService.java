package com.talent.expense_manager.expense_manager.service;

import com.talent.expense_manager.expense_manager.request.AccountRequest;
import com.talent.expense_manager.expense_manager.request.LoginRequest;
import com.talent.expense_manager.expense_manager.request.RefreshTokenRequest;
import com.talent.expense_manager.expense_manager.response.AccountResponse;
import com.talent.expense_manager.expense_manager.response.TokenResponseDto;

public interface AuthService {

    public AccountResponse login(LoginRequest request);

//    void logout(String accountId);

    public AccountResponse createAccount(AccountRequest request);

    public TokenResponseDto refreshToken(RefreshTokenRequest request);
}
