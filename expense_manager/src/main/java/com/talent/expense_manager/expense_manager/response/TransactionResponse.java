package com.talent.expense_manager.expense_manager.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;



@Getter
@Setter
@NoArgsConstructor
public class TransactionResponse {

    private Long id;
    private BigDecimal amount;
    private Enum type;
    private Enum description;
    private LocalDate date;


}
