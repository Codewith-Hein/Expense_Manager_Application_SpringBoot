package com.talent.expense_manager.expense_manager.config;

import com.talent.expense_manager.expense_manager.model.Account;
import com.talent.expense_manager.expense_manager.model.Permission;
import com.talent.expense_manager.expense_manager.model.Role;
import com.talent.expense_manager.expense_manager.model.Wallet;
import com.talent.expense_manager.expense_manager.repository.AccountRepository;
import com.talent.expense_manager.expense_manager.repository.PermissionRepository;
import com.talent.expense_manager.expense_manager.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;


@Profile("!test")
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {


        private final RoleRepository roleRepository;
        private final PermissionRepository permissionRepository;
        private final AccountRepository accountRepository;
        private final PasswordEncoder passwordEncoder;

        @Override
        public void run(String... args) throws Exception {

            // Create roles
            if (roleRepository.count() == 0) {
                Role userRole = new Role();
                userRole.setName("USER");
                roleRepository.save(userRole);

                Role adminRole = new Role();
                adminRole.setName("ADMIN");
                // attach all permissions
                adminRole.setPermissions(new HashSet<>(permissionRepository.findAll()));
                roleRepository.save(adminRole);
            }

            // Create default admin account
            if (accountRepository.findByEmail("admin@admin.com").isEmpty()) {
                Role adminRole = roleRepository.findByName("ADMIN").get();

                Account admin = new Account();
                admin.setAccountId("0001");
                admin.setName("Super Admin");
                admin.setEmail("admin@admin.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(adminRole);
                admin.setActive(true);

                Wallet wallet = new Wallet();
                wallet.setBalance(BigDecimal.ZERO);
                wallet.setBudget(BigDecimal.ZERO);
                wallet.setAccount(admin);
                admin.setWallet(wallet);

                accountRepository.save(admin);
            }
        }
}
