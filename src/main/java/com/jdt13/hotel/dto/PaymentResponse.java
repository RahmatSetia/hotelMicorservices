package com.jdt13.hotel.dto;

import lombok.Data;

@Data
public class PaymentResponse {
    private Integer idBooking;
    private boolean statusPembayaran;

}
