package com.talent.expense_manager.expense_manager.servicelmpl;

import com.talent.expense_manager.expense_manager.model.Account;
import com.talent.expense_manager.expense_manager.model.Enum.TransactionType;
import com.talent.expense_manager.expense_manager.model.Transaction;
import com.talent.expense_manager.expense_manager.model.Wallet;
import com.talent.expense_manager.expense_manager.repository.AccountRepository;
import com.talent.expense_manager.expense_manager.repository.TransactionRepository;
import com.talent.expense_manager.expense_manager.repository.WalletRepository;
import com.talent.expense_manager.expense_manager.request.TransactionRequest;
import com.talent.expense_manager.expense_manager.response.MonthlySummaryResponse;
import com.talent.expense_manager.expense_manager.response.TransactionListResponse;
import com.talent.expense_manager.expense_manager.response.TransactionResponse;
import com.talent.expense_manager.expense_manager.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(TransactionServiceImpl.class);


    @Autowired
    public AccountRepository accountRepository;

    @Autowired
    public TransactionRepository transactionRepository;


    @Autowired
    public WalletRepository walletRepository;

    @Override
    public void createTrasaction(String accountId, TransactionRequest request) {



        Account account = accountRepository.findByAccountId(accountId).orElseThrow(() -> new RuntimeException("Account not found"));

        Wallet wallet = account.getWallet();

        Transaction transaction = new Transaction();


        transaction.setWallet(wallet);
        transaction.setTransactionType(request.getTransactionType());
        transaction.setCategoryType(request.getCategoryType());
        transaction.setAmount(request.getAmount());

        if (request.getTransactionType() == TransactionType.INCOME) {
            wallet.setBalance(wallet.getBalance().add(request.getAmount()));

        } else if (request.getTransactionType() == TransactionType.EXPENSE) {
            if (wallet.getBudget().compareTo(request.getAmount()) < 0) {
                throw new RuntimeException("Insufficient balance");
            }

            wallet.setBudget(wallet.getBudget().subtract(request.getAmount()));


        }

        transactionRepository.save(transaction);
        walletRepository.save(wallet);


    }

    @Override
    public TransactionResponse findTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        return mapToResponse(transaction);
    }

    @Override
    public TransactionListResponse getAllTransactions(String accountId) {
        Account account = accountRepository.findByAccountId(accountId).orElseThrow(() -> new RuntimeException("Account with ID " + accountId + " not found"));


        Wallet wallet = account.getWallet();

        List<Transaction> transactions = transactionRepository.findByWallet(wallet);

        List<TransactionResponse> transactionResponses = transactions.stream().map(this::mapToResponse).toList();

        TransactionListResponse response = new TransactionListResponse();

        response.setTotalBalance(wallet.getBalance());
        response.setBudget(wallet.getBudget());
        response.setTransactions(transactionResponses);

        return response;


    }

    @Override
    public void deleteTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new RuntimeException("Account Not Found"));

        Wallet wallet = transaction.getWallet();

        if (transaction.getTransactionType()==TransactionType.INCOME) {

            BigDecimal newBalance= wallet.getBalance().subtract(transaction.getAmount());

            if (newBalance.compareTo(BigDecimal.ZERO)< 0){
                throw new RuntimeException("Cannot delete transaction. Balance would become negative.");
            }

            wallet.setBalance(newBalance);
        } else {
            wallet.setBalance(wallet.getBalance().add(transaction.getAmount()));
        }

        walletRepository.save(wallet);
        transactionRepository.delete(transaction);

    }

    @Override
    public void updateTransaction(Long transactionId, TransactionRequest request) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new RuntimeException("Transaction Id Not Found"));

        Wallet wallet = transaction.getWallet();

        if (transaction.getTransactionType() == TransactionType.INCOME) {
            wallet.setBalance(wallet.getBalance().subtract(transaction.getAmount()));
        } else {
            wallet.setBalance(wallet.getBalance().add(transaction.getAmount()));
        }


        transaction.setTransactionType(request.getTransactionType());
        transaction.setCategoryType(request.getCategoryType());
        transaction.setAmount(request.getAmount());

        if (request.getTransactionType() == TransactionType.INCOME) {
            wallet.setBalance(wallet.getBalance().add(request.getAmount()));
        } else {
            wallet.setBalance(wallet.getBalance().subtract(request.getAmount()));
        }

        walletRepository.save(wallet);
        transactionRepository.save(transaction);


    }

    @Override
    public MonthlySummaryResponse getMonthlySumary(String accountId, int year, int month) {
        Account account = accountRepository.findByAccountId(accountId).orElseThrow(() -> new RuntimeException("Account Not Found"));

        Wallet wallet = account.getWallet();

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());


        List<Transaction> transactions = transactionRepository.findByWalletAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(wallet, startDate.atStartOfDay(), endDate.atTime(23, 59, 59));

        return getMonthlySummaryResponse(transactions);

    }

    private static @NonNull MonthlySummaryResponse getMonthlySummaryResponse(List<Transaction> transactions) {
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            System.out.println("CreatedAt: " + transaction.getCreatedAt());
            if (transaction.getTransactionType() == TransactionType.INCOME) {
                totalIncome = totalIncome.add(transaction.getAmount());
            } else {
                totalExpense = totalExpense.add(transaction.getAmount());
            }
        }
        MonthlySummaryResponse response = new MonthlySummaryResponse();
        response.setTotalIncome(totalIncome);
        response.setTotalExpense(totalExpense);
        response.setNetAmount(totalIncome.subtract(totalExpense));
        return response;
    }


    private TransactionResponse mapToResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();

        response.setId(transaction.getId());
        response.setAmount(transaction.getAmount());
        response.setType(transaction.getTransactionType());
        response.setDescription(transaction.getCategoryType());
        response.setDate(LocalDate.from(transaction.getCreatedAt()));

        return response;
    }


}
