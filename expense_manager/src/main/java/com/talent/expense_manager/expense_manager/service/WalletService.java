package com.talent.expense_manager.expense_manager.service;

import com.talent.expense_manager.expense_manager.request.AddBudgetRequest;
import com.talent.expense_manager.expense_manager.request.WalletRequest;
import com.talent.expense_manager.expense_manager.response.WalletResponse;

import java.math.BigDecimal;

public interface WalletService {

WalletResponse myWallet(String accountId);

void addMyBudget(String accountId, AddBudgetRequest request);

void updateBudget(String accountId,AddBudgetRequest request);

void addBalance(Long walletId, BigDecimal amount);

void withDrawBlance(Long walletId,BigDecimal amount);

void deleteWallet(Long walletId);

WalletResponse createWallet(String accountId,WalletRequest request);





}
