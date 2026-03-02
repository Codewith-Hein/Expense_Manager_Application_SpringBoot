package com.talent.expense_manager.expense_manager.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AddBudgetRequest {


    @NotNull(message = "require budget")
    @Positive(message = "amount must be greater than 0")
    private BigDecimal myBudget;


}
