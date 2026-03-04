package com.talent.expense_manager.expense_manager;


import com.talent.expense_manager.expense_manager.config.DataInitializer;
import com.talent.expense_manager.expense_manager.model.Account;
import com.talent.expense_manager.expense_manager.model.Role;
import com.talent.expense_manager.expense_manager.repository.AccountRepository;
import com.talent.expense_manager.expense_manager.repository.RoleRepository;
import com.talent.expense_manager.expense_manager.security.JwtAuthenticationFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;




import static org.junit.jupiter.api.Assertions.*;



@ActiveProfiles("test")
@SpringBootTest
class ExpenseManagerApplicationTests {




	@Autowired
	private DataInitializer dataInitializer;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

    @Test
	void contextLoads() {
	}


	@Test
	void testRolesCreated() throws Exception {

		dataInitializer.run();

		// Check that both roles exist
		Role userRole = roleRepository.findByName("USER").orElse(null);
		Role adminRole = roleRepository.findByName("ADMIN").orElse(null);

		assertNotNull(userRole, "USER role should exist");
		assertNotNull(adminRole, "ADMIN role should exist");
	}

	@Test
	void testAdminAccountCreated() throws Exception {

		dataInitializer.run();

		// Check admin account
		Account admin = accountRepository.findByEmail("admin@admin.com")
				.orElseThrow(() -> new RuntimeException("Admin not found"));

		assertEquals("Super Admin", admin.getName());
		assertEquals("ADMIN", admin.getRole().getName());
		assertTrue(passwordEncoder.matches("admin123", admin.getPassword()));


	}






}
