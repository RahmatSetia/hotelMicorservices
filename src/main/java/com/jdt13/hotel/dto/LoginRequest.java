package com.jdt13.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "username harus di isi")
    private String username;
    @NotBlank(message = "password harus di isi")
    private String password;
}
