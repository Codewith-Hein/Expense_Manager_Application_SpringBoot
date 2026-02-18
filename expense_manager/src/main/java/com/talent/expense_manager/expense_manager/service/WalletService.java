package com.talent.expense_manager.expense_manager.service;

import com.talent.expense_manager.expense_manager.request.AddBudgetRequest;
import com.talent.expense_manager.expense_manager.response.WalletResponse;

public interface WalletService {

WalletResponse myWallet(String accountId);

void addMyBudget(String accountId, AddBudgetRequest request);





}
