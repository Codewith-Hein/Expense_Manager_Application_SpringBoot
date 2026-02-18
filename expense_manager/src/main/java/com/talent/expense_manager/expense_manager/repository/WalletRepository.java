package com.talent.expense_manager.expense_manager.repository;


import com.talent.expense_manager.expense_manager.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {

    Optional<Wallet> findByAccount_AccountId(String accountId);
}
