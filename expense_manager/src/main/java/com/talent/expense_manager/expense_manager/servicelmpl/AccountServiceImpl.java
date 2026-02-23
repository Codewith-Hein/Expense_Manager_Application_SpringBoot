package com.talent.expense_manager.expense_manager.servicelmpl;

import com.talent.expense_manager.expense_manager.exception.InvalidPasswordException;
import com.talent.expense_manager.expense_manager.model.Account;
import com.talent.expense_manager.expense_manager.model.Wallet;
import com.talent.expense_manager.expense_manager.repository.AccountRepository;
import com.talent.expense_manager.expense_manager.request.AccountRequest;
import com.talent.expense_manager.expense_manager.request.ChangePasswordRequest;
import com.talent.expense_manager.expense_manager.response.AccountResponse;
import com.talent.expense_manager.expense_manager.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
   public AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountResponse createAccount(AccountRequest request) {

        if (accountRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exist");
        }

        Account account = new Account();

        account.setAccountId(request.getAccountId());
        account.setName(request.getName());
        account.setDateOfBirth(request.getDateOfBirth());
        account.setEmail(request.getEmail());
        account.setPassword(passwordEncoder.encode(request.getPassword()));


        Wallet wallet=new Wallet();

        wallet.setBalance(BigDecimal.ZERO);
        wallet.setBudget(BigDecimal.ZERO);
        wallet.setAccount(account);

        account.setWallet(wallet);



        Account saveAccount = accountRepository.save(account);

        AccountResponse response = new AccountResponse();

        response.setAccountId(saveAccount.getAccountId());
        response.setName(saveAccount.getName());
        response.setDateOfBirth(saveAccount.getDateOfBirth());
        response.setEmail(saveAccount.getEmail());

        return response;


    }

    @Override
    public void accountDelete(String accountId) {

        Account account = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        accountRepository.delete(account);
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();

        return accounts.stream().map(account -> {
            AccountResponse response = new AccountResponse();

            response.setAccountId(account.getAccountId());
            response.setName(account.getName());
            response.setDateOfBirth(account.getDateOfBirth());
            response.setEmail(account.getEmail());

            return response;
        }).toList();

    }

    @Override
    public AccountResponse updateAccount(String accountId, AccountRequest request) {
        Account existingAccount = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        existingAccount.setName(request.getName());
        existingAccount.setDateOfBirth(request.getDateOfBirth());
        existingAccount.setEmail(request.getEmail());
        existingAccount.setPassword(request.getPassword());

        Account savedAccount = accountRepository.save(existingAccount);

        AccountResponse response = new AccountResponse();
        response.setAccountId(savedAccount.getAccountId());
        response.setName(savedAccount.getName());
        response.setDateOfBirth(savedAccount.getDateOfBirth());
        response.setEmail(savedAccount.getEmail());

        return response;
    }

    @Override
    public void changePassword(String accountId, ChangePasswordRequest request) {
        Account account = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

       if(!passwordEncoder.matches(request.getCurrentPassword(),account.getPassword())){
           throw new RuntimeException("Old password is incorrect");
       }
        account.setPassword(passwordEncoder.encode(request.getNewPassword()));

        accountRepository.save(account);

    }


    @Override
    public boolean login(String email, String password) {

        Account account=accountRepository.findByEmail(email).orElseThrow(()->new RuntimeException("incorrect email "));

        if(!passwordEncoder.matches(password,account.getPassword())){
            throw new RuntimeException("Invalid password");
        }


        return true;
    }


    @Override
    public void logout(String accountId) {
        System.out.println("User logged out: " + accountId);
    }
}
