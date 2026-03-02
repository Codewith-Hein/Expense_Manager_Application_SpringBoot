package com.talent.expense_manager.expense_manager.servicelmpl;


import com.talent.expense_manager.expense_manager.model.AuditLog;
import com.talent.expense_manager.expense_manager.repository.AuditLogRepository;
import com.talent.expense_manager.expense_manager.response.AuditResponse;
import com.talent.expense_manager.expense_manager.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public void log(String action, String entityName, String entityId, String details, String performedBy) {
        AuditLog log = AuditLog.builder()
                .action(action)
                .entityName(entityName)
                .entityId(entityId)
                .details(details)
                .performedBy(performedBy)
                .timestamp(LocalDateTime.now())
                .build();

        auditLogRepository.save(log);
    }

    @Override
    public List<AuditResponse> getAllLogs() {
        return auditLogRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<AuditResponse> getLogsByUser(String accountId) {
        return auditLogRepository.findByPerformedBy(accountId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    private AuditResponse mapToResponse(AuditLog log) {

        return AuditResponse.builder()
                .action(log.getAction())
                .entityName(log.getEntityName())
                .entityId(log.getEntityId())
                .details(log.getDetails())
                .performedBy(log.getPerformedBy())
                .timestamp(log.getTimestamp())
                .build();
    }


}
