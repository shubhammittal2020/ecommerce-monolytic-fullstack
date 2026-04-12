package com.mittal.shopping.common.response;

public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;

    public ApiResponse(boolean b, String userRegisteredSuccessfully, T userResponse) {
        this.success = b;
        this.message = userRegisteredSuccessfully;
        this.data = userResponse;
    }
}
