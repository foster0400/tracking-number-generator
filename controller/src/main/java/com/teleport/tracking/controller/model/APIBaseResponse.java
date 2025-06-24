package com.teleport.tracking.controller.model;

import com.teleport.tracking.controller.constant.ResponseStatusCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Value
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class APIBaseResponse<T> implements Serializable {
    int code;
    String message;
    transient T value;
    List<String> errors;

    public APIBaseResponse(T value) {
        this.code = ResponseStatusCode.SUCCESS.getHttpCode();
        this.message = HttpStatus.OK.getReasonPhrase();
        this.value = value;
        this.errors = null;
    }

    public APIBaseResponse(ResponseStatusCode responseStatusCode, List<String> errors) {
        this.code = responseStatusCode.getHttpCode();
        this.message = responseStatusCode.getMessage();
        this.value = null;
        this.errors = errors;
    }
}