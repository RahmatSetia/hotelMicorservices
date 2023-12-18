package com.jdt13.hotel.dto;

import com.jdt13.hotel.entity.Receptionist;
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
