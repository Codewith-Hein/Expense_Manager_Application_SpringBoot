package com.talent.expense_manager.expense_manager.response;

import com.talent.expense_manager.expense_manager.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private String accountId;
    private String name;
    private LocalDate dateOfBirth;
    private String email;
    private WalletInfo walletInfo;
    private String role;
    private TokenResponseDto tokenResponseDto;


}
