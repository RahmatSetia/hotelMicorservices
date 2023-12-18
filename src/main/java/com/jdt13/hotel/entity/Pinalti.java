package com.jdt13.hotel.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
public class Pinalti {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToOne
    private Booking booking;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date dateCheckout;
    @OneToOne
    private Receptionist receptionist;
    private BigDecimal denda;
}
