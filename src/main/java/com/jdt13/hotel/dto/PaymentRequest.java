package com.jdt13.hotel.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class PaymentRequest {
    @NotBlank
    private Integer idBooking;
}
