package com.assignment.usmobile.exception;

import lombok.Data;

@Data
public class ErrorResponse {

    private String errorMessage;

    public ErrorResponse() {}

    public ErrorResponse(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
