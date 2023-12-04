package com.jdt13.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerRequest {
    @NotBlank(message = "Nama harus di isi")
    @NotNull(message = "Nama tidak tidak boleh Null")
    private String nama;
    @NotBlank(message = "Password tidak boleh kosong")
    private String password;
    @NotBlank(message = "Username tidak boleh kosong")
    private String username;
    @NotBlank(message = "No handphone tidak boleh kosong")
    @Pattern(regexp = "^\\+?[0-9]{1,13}$", message = "Format tidak Valid")
    private String phone;
    @NotBlank(message = "alamat tidak boleh kosong")
    private String alamat;
}
