package com.talent.expense_manager.expense_manager.response;

import lombok.*;

import java.time.LocalDateTime;



@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditResponse {


    private String action;
    private String entityName;
    private String entityId;
    private String details;
    private String performedBy;
    private LocalDateTime timestamp;
}
