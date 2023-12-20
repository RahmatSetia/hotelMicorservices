package com.jdt13.hotel.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class ReceptionistRequest {
    @NotNull
    private String nama;
    @NotNull
    private String username;
    @NotNull
    private String password;
}
