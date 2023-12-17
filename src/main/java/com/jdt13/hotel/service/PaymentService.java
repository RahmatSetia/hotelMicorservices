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
        payment.setBooking(request);
        payment.setStatusPembayaran(false);
        paymentRepository.save(payment);

    //deleteById
    public String deletePaymentById (Integer id){
        String ok = "berhasil delete Paymennt dengan id = " + id;
        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isEmpty()){throw new ApiRequestException(pesan);}
        paymentRepository.deleteById(id);
        return ok;
    }

    private PaymentResponse toPaymentResponse (Payment payment){
        PaymentResponse response = new PaymentResponse();
        response.setStatusPembayaran(payment.getStatusPembayaran());
        return response;
    }
}
