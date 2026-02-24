package com.talent.expense_manager.expense_manager.exception;

import com.talent.expense_manager.expense_manager.response.BaseResponse;
import com.talent.expense_manager.expense_manager.response.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse<String>> handleException(RuntimeException ex){
        return ResponseUtil.success(
                HttpStatus.BAD_REQUEST,
                "ERROR",
                "GLOBAL",
                ex.getMessage(),
                null
        );
    }



}
