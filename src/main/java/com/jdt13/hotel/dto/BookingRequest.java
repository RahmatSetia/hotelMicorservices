package com.jdt13.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class BookingRequest {
    @NotBlank(message = "Customer ID tidak valid")
    private Integer customerId;
    @NotBlank(message = "Kamar ID tidak valid")
    private Integer kamarId;
    @NotBlank(message = "Tanggal booking tidak valid")
    private Date tanggalBooking;
}
