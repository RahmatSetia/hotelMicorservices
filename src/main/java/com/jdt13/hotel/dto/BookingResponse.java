package com.jdt13.hotel.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class BookingResponse {
    private Integer id;
    private Integer kamarId;
    private Integer customerId;
    private Date checkin;
    private Date checkout;
    private BigDecimal totalHarga;
    private Boolean statusBooking;
}
