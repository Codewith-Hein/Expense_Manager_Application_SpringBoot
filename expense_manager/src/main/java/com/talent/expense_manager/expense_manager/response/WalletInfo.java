package com.talent.expense_manager.expense_manager.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
public class WalletInfo {

    private BigDecimal walletBalance;
    private BigDecimal budget;
}
