package com.jdt13.hotel.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class KamarRequest {
    private Integer noKamar;
    private BigDecimal harga;
    @NotBlank(message = "Kategori kamar tidak valid")
    private String kategori;
    @NotBlank(message = "Deskripsi kamar tidak valid")
    private String deskripsi;
}
