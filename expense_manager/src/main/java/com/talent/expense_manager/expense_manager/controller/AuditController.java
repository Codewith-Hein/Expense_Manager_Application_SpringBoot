package com.talent.expense_manager.expense_manager.controller;


import com.talent.expense_manager.expense_manager.response.AuditResponse;
import com.talent.expense_manager.expense_manager.response.BaseResponse;
import com.talent.expense_manager.expense_manager.response.ResponseUtil;
import com.talent.expense_manager.expense_manager.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audit")
@RequiredArgsConstructor
public class AuditController {

private final AuditService auditService;



    @GetMapping
    public ResponseEntity<BaseResponse<List<AuditResponse>>> getAllLogs(){

        auditService.getAllLogs();

        return ResponseUtil.success(
                HttpStatus.OK,
                "getAllLogs",
                "Get",
                "ALl activity logs retrieved",
              auditService.getAllLogs()
        );



    }


    @GetMapping("/user/{accountId}")
    public ResponseEntity<BaseResponse<List<AuditResponse>>> getUserLogs(
            @PathVariable String accountId) {

        auditService.getLogsByUser(accountId);

        return ResponseUtil.success(
                HttpStatus.OK,
                "getAllLogs",
                "Get",
                "User activity logs retrieved",
                auditService.getLogsByUser(accountId)
        );

    }


}
