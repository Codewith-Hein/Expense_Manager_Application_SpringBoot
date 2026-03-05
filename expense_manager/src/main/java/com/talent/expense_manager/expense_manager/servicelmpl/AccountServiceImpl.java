package com.talent.expense_manager.expense_manager.servicelmpl;

import com.talent.expense_manager.expense_manager.exception.AccountNotFound;
import com.talent.expense_manager.expense_manager.model.Account;
import com.talent.expense_manager.expense_manager.model.Role;
import com.talent.expense_manager.expense_manager.model.Wallet;
import com.talent.expense_manager.expense_manager.repository.AccountRepository;
import com.talent.expense_manager.expense_manager.repository.RoleRepository;
import com.talent.expense_manager.expense_manager.request.AccountRequest;
import com.talent.expense_manager.expense_manager.request.ChangePasswordRequest;
import com.talent.expense_manager.expense_manager.response.AccountResponse;
import com.talent.expense_manager.expense_manager.response.TokenResponseDto;
import com.talent.expense_manager.expense_manager.response.WalletInfo;
import com.talent.expense_manager.expense_manager.response.WalletResponse;
import com.talent.expense_manager.expense_manager.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    public final AccountRepository accountRepository;

    private final RoleRepository roleRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;


    @Override
    public void accountDelete(String accountId) {

        LOGGER.info("[{}] deleteAccount() - deleting account - ACCOUNT_ID={}"
                , this.getClass().getSimpleName(), accountId);

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

        LOGGER.info("[{}] updateAccount() - ACTION=START- updating account - ACCOUNT_ID={}"
                , this.getClass().getSimpleName(), accountId);


        Account existingAccount = accountRepository.findByAccountId(accountId).orElseThrow(() -> new AccountNotFound("Account not found"));

        existingAccount.setName(request.getName());
        existingAccount.setDateOfBirth(request.getDateOfBirth());
        existingAccount.setEmail(request.getEmail());
        existingAccount.setPassword(passwordEncoder.encode(request.getPassword()));

        Account savedAccount = accountRepository.save(existingAccount);

        AccountResponse response = new AccountResponse();
        response.setAccountId(savedAccount.getAccountId());
        response.setName(savedAccount.getName());
        response.setDateOfBirth(savedAccount.getDateOfBirth());
        response.setEmail(savedAccount.getEmail());


        LOGGER.info("[{}] updateAccount() - ACTION=SUCCESS - updating account - NEW EMAIL={} - ACCOUNT_ID={}"
                , this.getClass().getSimpleName(), request.getEmail(), accountId);


        return response;
    }

    @Override
    public void changePassword(String accountId, ChangePasswordRequest request) {


        LOGGER.info("[{}] changePassword() - ACTION=START - changing password - ACCOUNT_ID={}"
                , this.getClass().getSimpleName(), accountId);


        Account account = accountRepository.findByAccountId(accountId).orElseThrow(() -> new AccountNotFound("Account not found"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), account.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }


        account.setPassword(passwordEncoder.encode(request.getNewPassword()));


        LOGGER.info("[{}] changePassword() - ACTION=SUCCESS - changing password - ACCOUNT_ID={}"
                , this.getClass().getSimpleName(), accountId);


        accountRepository.save(account);

    }

    @Override
    public AccountResponse accountInfo(String accountId) {

        LOGGER.info("[{}] accountInfo() - ACTION=START - Fetching Account Info - ACCOUNT_ID={}"
                , this.getClass().getSimpleName(), accountId);
        Account account = accountRepository.findByAccountId(accountId).orElseThrow(() -> new AccountNotFound("Account Not Found"));

        AccountResponse response = new AccountResponse();

        LOGGER.info("[{}] accountInfo() - ACTION=SUCCESS - Fetching Account Info - ACCOUNT_ID={}"
                , this.getClass().getSimpleName(), accountId);

        return buildAccountResponse(account);

    }


    @Override
    public void logout(String accountId) {
        LOGGER.info("Logout user attempt {}", accountId);
    }


    private AccountResponse buildAccountResponse(Account account) {

        WalletInfo walletInfo = new WalletInfo(account.getWallet().getBalance(), account.getWallet().getBudget());

        TokenResponseDto tokenResponseDto = new TokenResponseDto();

        return new AccountResponse(account.getAccountId(), account.getName(), account.getDateOfBirth(), account.getEmail(), walletInfo, account.getRole().getName(), tokenResponseDto);
    }
}
