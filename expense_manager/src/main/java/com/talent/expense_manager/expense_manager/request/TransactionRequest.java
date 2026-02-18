package com.talent.expense_manager.expense_manager.request;

import com.talent.expense_manager.expense_manager.model.Enum.CategoryType;
import com.talent.expense_manager.expense_manager.model.Enum.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
public class TransactionRequest {

    private TransactionType transactionType;
    private CategoryType categoryType;
    private BigDecimal amount;

}
