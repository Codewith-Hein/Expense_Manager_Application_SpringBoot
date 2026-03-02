package com.talent.expense_manager.expense_manager.Utils;

import com.talent.expense_manager.expense_manager.model.Account;
import com.talent.expense_manager.expense_manager.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final AccountRepository accountRepository;

    public Account getCurrentAccount(String accountId){
        String currentAccountId=accountId;

        return accountRepository.findByAccountId(currentAccountId)
                .orElseThrow(()->new RuntimeException("Current User Not Found"));
    }

}
