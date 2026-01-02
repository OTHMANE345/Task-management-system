package com.taskService.common.response;

import org.springframework.http.HttpStatus;

public record StandardResponse<T>
   (T data,
    Boolean success,
    String message,
    Object errors,
    HttpStatus httpStatus) {}


