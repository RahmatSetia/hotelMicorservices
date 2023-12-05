package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.PaymentRequest;
import com.jdt13.hotel.dto.PaymentResponse;
import com.jdt13.hotel.entity.Payment;
import com.jdt13.hotel.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentResponse paymentResponse(PaymentRequest request){
        Payment payment = new Payment();
        payment.setId(request.getIdBooking());
        payment.setBooking(payment.getBooking());
        payment.setStatusPembayaran(true);
        paymentRepository.save(payment);

        PaymentResponse response = new PaymentResponse();
        response.setStatusPembayaran(payment.getStatusPembayaran());
        return response;
    }
}
