package com.jdt13.hotel.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Pinalti {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToOne
    private Booking booking;
    private Date dateCheckout;
    @OneToOne
    private Receptionist receptionist;
}
