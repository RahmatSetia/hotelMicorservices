package com.jdt13.hotel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class BookingRequest {
    private Integer customerId;
    private Integer kamarId;
    @FutureOrPresent(message = "Tanggal CheckIn tidak valid")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date checkin;
    @FutureOrPresent(message = "Tanggal CheckOut tidak valid")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date checkout;
}
