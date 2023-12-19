package com.jdt13.hotel.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PinaltiResponse {
    private Integer id;
    private Integer bookingId;
    private Date dateCheckout;
    private Integer receptionist;
    private BigDecimal denda;
}
