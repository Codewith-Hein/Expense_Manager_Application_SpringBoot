package com.talent.expense_manager.expense_manager.exception;

import com.talent.expense_manager.expense_manager.response.BaseResponse;
import com.talent.expense_manager.expense_manager.response.ResponseUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse<String>> handleException(RuntimeException ex) {
        return ResponseUtil.success(
                HttpStatus.BAD_REQUEST,
                "ERROR",
                "GLOBAL",
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<BaseResponse<String>> handleException(JwtTokenExpiredException ex) {
        return ResponseUtil.success(
                HttpStatus.UNAUTHORIZED,
                "ERROR",
                "GLOBAL",
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleException(AccountNotFound ex) {
        return ResponseUtil.success(
                HttpStatus.UNAUTHORIZED,
                "ERROR",
                "GLOBAL",
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<BaseResponse<String>> handleException(InvalidRefreshTokenException ex) {
        return ResponseUtil.success(
                HttpStatus.UNAUTHORIZED,
                "ERROR",
                "GLOBAL",
                ex.getMessage(),
                null
        );
    }
    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<?> handleInvalidEmail(InvalidEmailException ex) {
        return ResponseUtil.success(
                HttpStatus.UNAUTHORIZED,
                "ERROR",
                "GLOBAL",
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<?> handleInvalidEmail(WalletNotFoundException ex) {
        return ResponseUtil.success(
                HttpStatus.UNAUTHORIZED,
                "ERROR",
                "GLOBAL",
                ex.getMessage(),
                null
        );
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<BaseResponse<String>> handleNotFound(NoHandlerFoundException ex) {

        return ResponseUtil.success(
                HttpStatus.NOT_FOUND,
                "ERROR",
                "GLOBAL",
                "API endpoint not found",
                null
        );
    }

}
