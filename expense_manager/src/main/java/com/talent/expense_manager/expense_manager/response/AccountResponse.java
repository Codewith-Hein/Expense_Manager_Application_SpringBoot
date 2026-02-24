package com.talent.expense_manager.expense_manager.response;

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


}
