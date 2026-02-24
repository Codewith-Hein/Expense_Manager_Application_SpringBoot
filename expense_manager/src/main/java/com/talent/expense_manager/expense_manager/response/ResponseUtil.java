package com.talent.expense_manager.expense_manager.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ResponseUtil {

    public static <T> ResponseEntity<BaseResponse<T>> success(
            HttpStatus status,
            String apiName,
            String apiId,
            String message,
            T data
    ){
        BaseResponse<T> response=BaseResponse.<T>builder()
                .httpStatusCode(status.value())
                .apiName(apiName)
                .apiId(apiId)
                .message(message)
                .systemDateTime(LocalDateTime.now())
                .data(data)
                .build();

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}
