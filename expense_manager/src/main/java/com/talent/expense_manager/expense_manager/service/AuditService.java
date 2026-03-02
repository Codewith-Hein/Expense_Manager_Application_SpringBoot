package com.talent.expense_manager.expense_manager.service;

import com.talent.expense_manager.expense_manager.response.AuditResponse;

import java.util.List;

public interface AuditService {


    void log(String action,
             String entityName,
             String entityId,
             String details,
             String performedBy);

    List<AuditResponse> getAllLogs();

    List<AuditResponse> getLogsByUser(String accountId);

}
