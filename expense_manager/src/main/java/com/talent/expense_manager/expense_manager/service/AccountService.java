package com.talent.expense_manager.expense_manager.service;

import com.talent.expense_manager.expense_manager.model.Account;
import com.talent.expense_manager.expense_manager.request.AccountRequest;
import com.talent.expense_manager.expense_manager.request.ChangePasswordRequest;
import com.talent.expense_manager.expense_manager.response.AccountResponse;

import java.util.List;


public interface AccountService {

    AccountResponse createAccount(AccountRequest request);

    public void accountDelete(String id);


    List<AccountResponse> getAllAccounts();


    public AccountResponse updateAccount(String accountId,AccountRequest request);

    void changePassword(String accountId, ChangePasswordRequest request);


    boolean login(String email, String password);

    void logout(String accountId);


}
