package com.talent.expense_manager.expense_manager.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDto {

    private String accessToken;
    private String refreshToken;



}
