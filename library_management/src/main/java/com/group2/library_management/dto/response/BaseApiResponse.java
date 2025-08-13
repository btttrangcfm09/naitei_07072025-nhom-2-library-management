package com.group2.library_management.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseApiResponse<T> {
    private int code; // mã trạng thái
    private T data; // dữ liệu trả về
    private String message; // thông điệp

    public BaseApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseApiResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }
}
