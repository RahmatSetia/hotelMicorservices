package com.jdt13.hotel.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private Customer customer;
    @OneToOne
    private Kamar kamar;
    private Date tanggalBooking;
    @OneToOne
    private Checkin checkin;
    @OneToOne
    private Checkout checkout;
    private BigDecimal totalHarga;
    private Boolean statusBooking;
}
