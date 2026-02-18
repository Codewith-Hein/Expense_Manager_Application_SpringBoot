package com.talent.expense_manager.expense_manager.servicelmpl;

import com.talent.expense_manager.expense_manager.model.Account;
import com.talent.expense_manager.expense_manager.model.Wallet;
import com.talent.expense_manager.expense_manager.repository.AccountRepository;
import com.talent.expense_manager.expense_manager.repository.WalletRepository;
import com.talent.expense_manager.expense_manager.request.AddBudgetRequest;
import com.talent.expense_manager.expense_manager.response.WalletResponse;
import com.talent.expense_manager.expense_manager.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    public AccountRepository accountRepository;

    @Autowired
    public WalletRepository walletRepository;

    @Override
    public WalletResponse myWallet(String accountId) {
     Account account=accountRepository.findByAccountId(accountId).orElseThrow(()->new RuntimeException("Account Not found"));

     Wallet wallet=account.getWallet();

     WalletResponse response=new WalletResponse();

     response.setAccountId(account.getAccountId());
     response.setBalance(wallet.getBalance());
     response.setMyBudget(wallet.getBudget());

     return response;


    }

    @Override
    public void addMyBudget(String accountId, AddBudgetRequest request) {
        Account account=accountRepository.findByAccountId(accountId).orElseThrow(()->new RuntimeException("Account Not found"));

        Wallet wallet=account.getWallet();


        wallet.setBudget(request.getMyBudget());

        walletRepository.save(wallet);

    }
}
