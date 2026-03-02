package com.talent.expense_manager.expense_manager.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {

    @NotBlank(message = "Required AccountId")
    private String accountId;

    @NotBlank(message = "Required RefreshToken")
    private String refreshToken;



}
