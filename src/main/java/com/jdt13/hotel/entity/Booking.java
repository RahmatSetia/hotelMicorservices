package com.jdt13.hotel.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Kamar kamar;
    private Date checkin;
    private Date checkout;
    private Date tanggalBooking;
    private BigDecimal totalHarga;
    private Boolean statusBooking;
}
