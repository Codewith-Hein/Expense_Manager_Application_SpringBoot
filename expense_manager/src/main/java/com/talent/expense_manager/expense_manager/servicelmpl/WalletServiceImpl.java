package com.talent.expense_manager.expense_manager.servicelmpl;

import com.talent.expense_manager.expense_manager.model.Account;
import com.talent.expense_manager.expense_manager.model.Wallet;
import com.talent.expense_manager.expense_manager.repository.AccountRepository;
import com.talent.expense_manager.expense_manager.repository.WalletRepository;
import com.talent.expense_manager.expense_manager.request.AddBudgetRequest;
import com.talent.expense_manager.expense_manager.request.WalletRequest;
import com.talent.expense_manager.expense_manager.response.WalletResponse;
import com.talent.expense_manager.expense_manager.service.WalletService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    public AccountRepository accountRepository;

    @Autowired
    public WalletRepository walletRepository;

    @Override
    public WalletResponse myWallet(String accountId) {

        Account account = accountRepository.findByAccountId(accountId).orElseThrow(() -> new RuntimeException("Account Not found"));

        Wallet wallet = walletRepository.findByAccountAndDeletedAtIsNull(account).orElseThrow(() -> new RuntimeException("Wallet not found"));

        WalletResponse response = new WalletResponse();
        response.setAccountId(account.getAccountId());
        response.setBalance(wallet.getBalance());
        response.setMyBudget(wallet.getBudget());
        response.setWalletId(wallet.getId());

        return response;
    }


    @Override
    public void addMyBudget(String accountId, AddBudgetRequest request) {
        Account account = accountRepository.findByAccountId(accountId).orElseThrow(() -> new RuntimeException("Account Not found"));

        Wallet wallet = account.getWallet();

        BigDecimal newBudget=request.getMyBudget();

        if (wallet.getBalance().compareTo(newBudget) < 0) {
            throw new RuntimeException("Not Enough Balance");
        }

        wallet.setBudget(wallet.getBudget().add(newBudget));

        wallet.setBalance(wallet.getBalance().subtract(newBudget));



        walletRepository.save(wallet);

    }

    @Override
    public void updateBudget(String accountId, AddBudgetRequest request) {
        Account account = accountRepository.findByAccountId(accountId).orElseThrow(() -> new RuntimeException("Account Not found"));

        Wallet wallet = account.getWallet();

        wallet.setBudget(request.getMyBudget());

        walletRepository.save(wallet);


    }

    @Override
    public void addBalance(Long walletId, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new RuntimeException("Wallet Not Found"));

        BigDecimal newBalance = wallet.getBalance().add(amount);
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);


    }

    @Override
    public void withDrawBlance(Long walletId, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet Not Found"));


        if ((amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Amount");
        }


        if (amount.compareTo(wallet.getBalance()) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Insufficient Balance");
        }
        BigDecimal newBalance = wallet.getBalance().subtract(amount);


        wallet.setBalance(newBalance);
        walletRepository.save(wallet);

    }

    @Override
    @Transactional
    public void deleteWallet(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new RuntimeException("Invalid Wallet id"));

        wallet.setDeletedAt(LocalDateTime.now());

        walletRepository.save(wallet);

    }

    @Override
    @Transactional
    public WalletResponse createWallet(String accountId, WalletRequest request) {
        Account account = accountRepository.findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // 🔥 IMPORTANT — find ANY wallet (even deleted)
        Optional<Wallet> existingWallet = walletRepository.findByAccount(account);

        if (existingWallet.isPresent()) {

            Wallet wallet = existingWallet.get();

            if (wallet.getDeletedAt() != null) {
                // RESTORE
                wallet.setDeletedAt(null);
                wallet.setBalance(request.getAmount());
                wallet.setBudget(request.getBudget());
                return mapToResponse(walletRepository.save(wallet));
            } else {
                throw new RuntimeException("Wallet already exists");
            }
        }

        // Only create if truly no wallet row exists
        Wallet wallet = new Wallet();
        wallet.setAccount(account);
        wallet.setBalance(request.getAmount());
        wallet.setBudget(request.getBudget());

        return mapToResponse(walletRepository.save(wallet));
    }


    private WalletResponse mapToResponse(Wallet wallet) {

        WalletResponse response = new WalletResponse();

        response.setAccountId(wallet.getAccount().getAccountId());
        response.setBalance(wallet.getBalance());
        response.setMyBudget(wallet.getBudget());

        return response;
    }


}



