package com.talent.expense_manager.expense_manager.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="audit_log")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;        // CREATE_TRANSACTION

    private String entityName;    // Transaction

    private String entityId;      // 10

    @Column(columnDefinition = "TEXT")
    private String details;       // Amount 5000 added

    private String performedBy;   // accountId

    private LocalDateTime timestamp;


}
