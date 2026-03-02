package com.talent.expense_manager.expense_manager.servicelmpl;

import com.talent.expense_manager.expense_manager.Utils.SecurityUtils;
import com.talent.expense_manager.expense_manager.exception.AccountNotFound;
import com.talent.expense_manager.expense_manager.model.Account;
import com.talent.expense_manager.expense_manager.model.Enum.TransactionType;
import com.talent.expense_manager.expense_manager.model.Permission;
import com.talent.expense_manager.expense_manager.model.Transaction;
import com.talent.expense_manager.expense_manager.model.Wallet;
import com.talent.expense_manager.expense_manager.repository.AccountRepository;
import com.talent.expense_manager.expense_manager.repository.TransactionRepository;
import com.talent.expense_manager.expense_manager.repository.WalletRepository;
import com.talent.expense_manager.expense_manager.request.TransactionRequest;
import com.talent.expense_manager.expense_manager.response.MonthlySummaryResponse;
import com.talent.expense_manager.expense_manager.response.TransactionListResponse;
import com.talent.expense_manager.expense_manager.response.TransactionResponse;
import com.talent.expense_manager.expense_manager.service.AuditService;
import com.talent.expense_manager.expense_manager.service.PermissoinService;
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
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static java.util.stream.Collectors.toList;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final AccountRepository accountRepository;


    private final TransactionRepository transactionRepository;

    private final AuditService auditService;
    @Autowired
    public WalletRepository walletRepository;

    private final SecurityUtils securityUtils;


    private final PermissoinService permissoinService;


    @Override
    public String createTrasaction(String accountId, TransactionRequest request) {

//        Account currentAccount = securityUtils.getCurrentAccount(accountId);
//
//        if(!permissoinService.hasPermission(currentAccount,"TRANSACTION","CREATE")){
//            throw new RuntimeException("You do not have permission to create a transaction.");
//        }

        Account account = accountRepository.findByAccountId(accountId).orElseThrow(() -> new AccountNotFound("Account not found"));

        Wallet wallet = account.getWallet();

        Transaction transaction = new Transaction();


        transaction.setWallet(wallet);
        transaction.setTransactionType(request.getTransactionType());
        transaction.setCategoryType(request.getCategoryType());
        transaction.setAmount(request.getAmount());


        String message = "Transaction successful";


        if (request.getTransactionType() == TransactionType.INCOME) {
            wallet.setBalance(wallet.getBalance().add(request.getAmount()));

        } else if (request.getTransactionType() == TransactionType.EXPENSE) {
            if (wallet.getBalance().compareTo(request.getAmount()) < 0) {
                throw new RuntimeException("Insufficient balance");
            }

            if (wallet.getBudget().compareTo(request.getAmount()) < 0) {
                message = "Warning: Your expense exceeded your budget!";
            }

            wallet.setBalance(wallet.getBalance().subtract(request.getAmount()));


        }

        transactionRepository.save(transaction);
        walletRepository.save(wallet);


        auditService.log("CREATE_TRANSACTION",
                "Transaction", transaction.getId().toString(),
                "Amount" + transaction.getAmount(),
                accountId);
        return message;
    }

//    @Override
//    public TransactionResponse findTransactionById(Long id) {
//        Transaction transaction = transactionRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Transaction not found"));
//
//
//        auditService.log("findTransactionById","Transaction",transaction.getId().toString(),"View Transaction",);
//
//        return mapToResponse(transaction);
//    }

    @Override
    public TransactionListResponse getAllTransactionsByAccountId(String accountId) {

//        Account currentAccount = securityUtils.getCurrentAccount(accountId);
//
//        if(!permissoinService.hasPermission(currentAccount,"TRANSACTION","VIEW")){
//            throw new RuntimeException("You do not have permission to View.");
//        }

        Account account = accountRepository.findByAccountId(accountId).orElseThrow(() -> new AccountNotFound("Account with ID " + accountId + " not found"));


        Wallet wallet = account.getWallet();

        List<Transaction> transactions = transactionRepository.findByWallet(wallet);

        List<TransactionResponse> transactionResponses = transactions.stream().map(this::mapToResponse).toList();

        TransactionListResponse response = new TransactionListResponse();

        response.setTotalBalance(wallet.getBalance());
        response.setBudget(wallet.getBudget());
        response.setTransactions(transactionResponses);
        response.setAccountId(wallet.getAccount().getAccountId());

        return response;


    }

    @Override
    public void deleteTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new RuntimeException("Invalid Id"));


        Wallet wallet = transaction.getWallet();

        if (transaction.getTransactionType() == TransactionType.INCOME) {

            BigDecimal newBalance = wallet.getBalance().subtract(transaction.getAmount());

            if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
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

//        Account currentAccount = securityUtils.getCurrentAccount(accountId);
//
//        if(!permissoinService.hasPermission(currentAccount,"TRANSACTION","VIEW")){
//            throw new RuntimeException("You do not have permission to view MonthlySummary");
//        }
        Account account = accountRepository.findByAccountId(accountId).orElseThrow(() -> new AccountNotFound("Account Not Found"));

        Wallet wallet = account.getWallet();

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());


        List<Transaction> transactions = transactionRepository.findByWalletAndCreatedDatetimeGreaterThanEqualAndCreatedDatetimeLessThan(wallet, startDate.atStartOfDay(), endDate.atTime(23, 59, 59));

        return getMonthlySummaryResponse(transactions);

    }

    @Override
    public List<TransactionResponse> viewAllTransaction() {

        return transactionRepository.findAll().stream().map(
                this::mapToResponse
        ).toList();
    }

//    @Override
//    public TransactionListResponse viewAllTransaction() {
//
//        Transaction transaction= (Transaction) transactionRepository.findAll();
//
//
//
//    }

    private static @NonNull MonthlySummaryResponse getMonthlySummaryResponse(List<Transaction> transactions) {
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            System.out.println("CreatedAt: " + transaction.getCreatedDatetime());
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

        response.setAccountId(transaction.getWallet().getAccount().getAccountId());
        response.setId(transaction.getId());
        response.setAmount(transaction.getAmount());
        response.setType(transaction.getTransactionType());
        response.setDescription(transaction.getCategoryType());
        response.setDate(LocalDate.from(transaction.getCreatedDatetime()));

        return response;
    }


}
