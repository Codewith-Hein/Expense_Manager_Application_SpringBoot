package com.talent.expense_manager.expense_manager.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String accountId;

    @Column(nullable = false)
    private String name;


    private LocalDate dateOfBirth;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;


    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (role == null || role.getName() == null) {
            return Collections.emptyList(); // no role assigned
        }

        // Add the ROLE_ prefix because Spring expects it
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase());

        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

}




