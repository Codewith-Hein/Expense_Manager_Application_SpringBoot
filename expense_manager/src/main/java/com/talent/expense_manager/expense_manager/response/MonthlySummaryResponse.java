package com.talent.expense_manager.expense_manager.response;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class MonthlySummaryResponse {
    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal netAmount;
}
