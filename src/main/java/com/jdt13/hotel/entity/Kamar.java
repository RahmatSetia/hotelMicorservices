package com.jdt13.hotel.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class Kamar {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer noKamar;
    private BigDecimal harga;
    private String kategori;
    private String deskripsi;
    private boolean status;
}
