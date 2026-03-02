package com.talent.expense_manager.expense_manager.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WalletRequest {


    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "Budget is required")
    @Positive(message = "Budget must be greater than 0")
    private BigDecimal budget;
}
