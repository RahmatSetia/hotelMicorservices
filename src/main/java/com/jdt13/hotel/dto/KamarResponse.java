package com.jdt13.hotel.dto;

import lombok.Data;

@Data
public class KamarResponse {

    private Integer id;
    private Integer noKamar;
    private Double harga;
    private String kategori;
    private String deskripsi;
}
