package com.talent.expense_manager.expense_manager.repository;

import com.talent.expense_manager.expense_manager.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByPerformedBy(String performedBy);

}
