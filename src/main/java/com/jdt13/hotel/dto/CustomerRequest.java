package com.jdt13.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerRequest {
    @NotBlank(message = "nama tidak boleh kosong")
    private String nama;
    @NotBlank(message = "password tidak boleh kosong")
    private String password;
    @NotBlank(message = "username tidak boleh kosong")
    private String username;
    @NotBlank(message = "no handphon tidak boleh kosong")
    private String phone;
    @NotBlank(message = "alamat tidak boleh kosong")
    private String alamat;
}
