package com.jdt13.hotel.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
@Data
public class LoginRequest {
    @NotBlank(message = "username harus di isi")
    @NotNull(message = "username harus di isi")
    private String username;
    @NotBlank(message = "password harus di isi")
    @NotNull(message = "password harus di isi")
    private String password;
}
