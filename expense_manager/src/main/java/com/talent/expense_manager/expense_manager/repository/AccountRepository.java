package com.talent.expense_manager.expense_manager.repository;

import com.talent.expense_manager.expense_manager.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {


    Optional<Account> findByEmail(String email);

    Optional<Account> findByAccountId(String accountId);


}
