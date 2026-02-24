package com.talent.expense_manager.expense_manager.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletResponse {

    private String accountId;
    private BigDecimal balance;
    private BigDecimal myBudget;
    private Long walletId;


}
