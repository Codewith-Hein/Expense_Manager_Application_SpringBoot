package com.talent.expense_manager.expense_manager.servicelmpl;

import com.talent.expense_manager.expense_manager.model.Account;
import com.talent.expense_manager.expense_manager.model.Wallet;
import com.talent.expense_manager.expense_manager.repository.AccountRepository;
import com.talent.expense_manager.expense_manager.request.AccountRequest;
import com.talent.expense_manager.expense_manager.request.ChangePasswordRequest;
import com.talent.expense_manager.expense_manager.response.AccountResponse;
import com.talent.expense_manager.expense_manager.response.WalletInfo;
import com.talent.expense_manager.expense_manager.response.WalletResponse;
import com.talent.expense_manager.expense_manager.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    public final AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


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


        Wallet wallet = new Wallet();

        wallet.setBalance(BigDecimal.ZERO);
        wallet.setBudget(BigDecimal.ZERO);
        wallet.setAccount(account);

        account.setWallet(wallet);


        Account saveAccount = accountRepository.save(account);

        AccountResponse response = new AccountResponse();

        return buildAccountResponse(account);


    }


    @Override
    public void accountDelete(String accountId) {

        Account account = accountRepository.findByAccountId(accountId).orElseThrow(() -> new RuntimeException("Account not found"));

        accountRepository.delete(account);
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();

        return accounts.stream().map(account -> {
            AccountResponse response = new AccountResponse();

            return buildAccountResponse(account);
        }).toList();

    }

    @Override
    public AccountResponse updateAccount(String accountId, AccountRequest request) {
        Account existingAccount = accountRepository.findByAccountId(accountId).orElseThrow(() -> new RuntimeException("Account not found"));

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
        Account account = accountRepository.findByAccountId(accountId).orElseThrow(() -> new RuntimeException("Account not found"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), account.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }
        account.setPassword(passwordEncoder.encode(request.getNewPassword()));

        accountRepository.save(account);

    }


    @Override
    public AccountResponse login(AccountRequest request) {

        LOGGER.info("Login attempt for account: {}", request.getAccountId());


        java.util.Optional<Account> accountOptional = accountRepository.findByEmail(request.getEmail());
        if (accountOptional.isEmpty()) {
            throw new RuntimeException("Email incorrect");
        }

        Account account = accountOptional.get();

        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        LOGGER.info("Login successful for account: {}", account.getAccountId());

        return buildAccountResponse(account);

    }


    @Override
    public void logout(String accountId) {
        System.out.println("User logged out: " + accountId);
    }


    private AccountResponse buildAccountResponse(Account account) {

        WalletInfo walletInfo = new WalletInfo(account.getWallet().getBalance(), account.getWallet().getBudget());

        return new AccountResponse(account.getAccountId(), account.getName(), account.getDateOfBirth(), account.getEmail(), walletInfo);
    }
}
