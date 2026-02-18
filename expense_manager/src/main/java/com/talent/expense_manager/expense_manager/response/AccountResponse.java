package com.talent.expense_manager.expense_manager.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class AccountResponse {
    private String accountId;
    private String name;
    private LocalDate dateOfBirth;
    private String email;


}
