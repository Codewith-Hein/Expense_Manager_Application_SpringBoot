package com.talent.expense_manager.expense_manager.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TransactionListResponse {

    private BigDecimal totalBalance;
    private List<TransactionResponse> transactions;


}
