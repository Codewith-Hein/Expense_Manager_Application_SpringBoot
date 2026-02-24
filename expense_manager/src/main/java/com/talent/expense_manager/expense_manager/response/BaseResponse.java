package com.talent.expense_manager.expense_manager.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class BaseResponse<T> {

    private int httpStatusCode;
    private String apiName;
    private String apiId;
    private String message;
    private LocalDateTime systemDateTime;
    private T data;
}
