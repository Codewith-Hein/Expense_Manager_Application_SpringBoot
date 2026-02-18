package com.talent.expense_manager.expense_manager.repository;

import com.talent.expense_manager.expense_manager.model.Account;
import com.talent.expense_manager.expense_manager.model.Transaction;
import com.talent.expense_manager.expense_manager.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    @Override
    Optional<Transaction> findById(Long aLong);


    List<Transaction> findByWallet(Wallet wallet);

    List<Transaction> findByWalletAndCreatedAtGreaterThanEqualAndCreatedAtLessThan(
            Wallet wallet,
            LocalDateTime start,
            LocalDateTime end);



}
