package com.talent.expense_manager.expense_manager.service;

import com.talent.expense_manager.expense_manager.request.TransactionRequest;
import com.talent.expense_manager.expense_manager.response.MonthlySummaryResponse;
import com.talent.expense_manager.expense_manager.response.TransactionListResponse;
import com.talent.expense_manager.expense_manager.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    String createTrasaction(String accountId, TransactionRequest request);



  public TransactionListResponse getAllTransactionsByAccountId(String accountId);


  void deleteTransaction(Long transactionId);

  void updateTransaction(Long transactionId,TransactionRequest request);

  MonthlySummaryResponse getMonthlySumary(String accountId,int year,int month);

  public List<TransactionResponse> viewAllTransaction();


}
