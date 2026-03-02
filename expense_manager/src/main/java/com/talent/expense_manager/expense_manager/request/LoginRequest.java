package com.talent.expense_manager.expense_manager.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class LoginRequest {

    @NotNull(message = "Email is require")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Require Password")
    private String password;
}
