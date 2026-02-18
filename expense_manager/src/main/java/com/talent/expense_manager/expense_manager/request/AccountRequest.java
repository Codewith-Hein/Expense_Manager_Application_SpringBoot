package com.talent.expense_manager.expense_manager.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class AccountRequest {

    private String accountId;
    private String name;
    private LocalDate dateOfBirth;
    private String email;
    private String password;


}
