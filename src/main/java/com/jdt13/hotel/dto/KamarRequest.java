package com.jdt13.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class KamarRequest {
    @NotBlank(message = "No kamar tidak valid")
    private Integer noKamar;
    @NotBlank(message = "Harga tidak valid")
    private BigDecimal harga;
    @NotBlank(message = "Kategori kamar tidak valid")
    private String kategori;
    @NotBlank(message = "Deskripsi kamar tidak valid")
    private String deskripsi;
}
