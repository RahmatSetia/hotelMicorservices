package com.jdt13.hotel.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class KamarResponse {

    private Integer id;
    private Integer noKamar;
    private BigDecimal harga;
    private String kategori;
    private String deskripsi;
}
