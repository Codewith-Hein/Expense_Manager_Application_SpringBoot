package com.talent.expense_manager.expense_manager.security;

import com.talent.expense_manager.expense_manager.model.Account;
import com.talent.expense_manager.expense_manager.model.Role;
import com.talent.expense_manager.expense_manager.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private AccountRepository accountRepository;


    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Account account = accountRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Account Not Found"));


        return new org.springframework.security.core.userdetails.User(
                account.getEmail(),
                account.getPassword(),
                mapToAuthorities(account.getRole())
        );


    }

    private Collection<? extends GrantedAuthority> mapToAuthorities(Role role) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(role.getName()));

        role.getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission_name()))
                .forEach(authorities::add);

        return authorities;
    }


}
