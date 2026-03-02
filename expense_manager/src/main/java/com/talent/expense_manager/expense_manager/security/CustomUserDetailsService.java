package com.talent.expense_manager.expense_manager.security;

import com.talent.expense_manager.expense_manager.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;


    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        return accountRepository
                .findByEmail(email)   // ✅ search by EMAIL
                .orElseThrow(() ->
                        new UsernameNotFoundException("Account Not Found"));
    }
}
