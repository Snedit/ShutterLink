package com.shutterlink.auth_service.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private int status;
    private String message;
    private T data;
    private Instant timestamp;
    private String path;

    // Convenience factory methods
    public static <T> ApiResponse<T> success(T data, String message, HttpStatus status, String path) {
        return ApiResponse.<T>builder()
                .success(true)
                .status(status.value())
                .message(message)
                .data(data)
                .timestamp(Instant.now())
                .path(path)
                .build();
    }

    public static <T> ApiResponse<T> error(String message, HttpStatus status, String path) {
        return ApiResponse.<T>builder()
                .success(false)
                .status(status.value())
                .message(message)
                .timestamp(Instant.now())
                .path(path)
                .build();
    }
}
