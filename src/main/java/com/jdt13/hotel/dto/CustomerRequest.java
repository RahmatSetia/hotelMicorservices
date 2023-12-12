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
    @NotBlank(message = "Password harus di isi")
    private String password;
    @NotBlank(message = "Username harus di isi")
    private String username;
    @NotBlank(message = "No handphone harus di isi")
    @Pattern(regexp = "^\\+?[0-9]{1,13}$", message = "Format phone tidak Valid")
    private String phone;
    @NotBlank(message = "Alamat harus di isi")
    private String alamat;
}
