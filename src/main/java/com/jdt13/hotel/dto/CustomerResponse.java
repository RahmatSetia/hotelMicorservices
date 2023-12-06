package com.jdt13.hotel.dto;

import lombok.Data;

@Data
public class CustomerResponse {
    private Integer id;
    private String nama;
    private String password;
    private String username;
    private String phone;
    private String alamat;
}
