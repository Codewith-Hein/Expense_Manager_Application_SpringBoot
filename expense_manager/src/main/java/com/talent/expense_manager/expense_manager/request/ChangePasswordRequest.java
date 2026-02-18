package com.talent.expense_manager.expense_manager.request;


import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordRequest {


    @Column(nullable = false)
    private String currentPassword;

    @Column(nullable = false)
    private String newPassword;
}
