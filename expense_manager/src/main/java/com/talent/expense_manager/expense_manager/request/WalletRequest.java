package com.talent.expense_manager.expense_manager.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletRequest {
    private BigDecimal amount;
    private BigDecimal budget;
}
