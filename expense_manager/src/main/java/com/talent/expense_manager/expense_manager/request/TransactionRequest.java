package com.talent.expense_manager.expense_manager.request;

import com.talent.expense_manager.expense_manager.model.Enum.CategoryType;
import com.talent.expense_manager.expense_manager.model.Enum.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
public class TransactionRequest {

    @NotNull(message = "Transaction type is required")
    private TransactionType transactionType;

    @NotNull(message = "Category type is required")
    private CategoryType categoryType;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    private BigDecimal amount;
}
