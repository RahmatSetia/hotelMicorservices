package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.PaymentResponse;
import com.jdt13.hotel.entity.Booking;
import com.jdt13.hotel.entity.Payment;
import com.jdt13.hotel.exception.ApiRequestException;
import com.jdt13.hotel.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private String pesan = "Id Payment tidak di temukan";
    public PaymentResponse addPayment(Booking request){
        Payment payment = new Payment();
        payment.setBooking(request);
        payment.setStatusPembayaran(false);
        paymentRepository.save(payment);
        return toPaymentResponse(payment);
    }
    //accPayment
    public PaymentResponse accPayment (Integer id){
        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isEmpty()){throw new ApiRequestException(pesan);}
        Payment pay = new Payment();
        pay.setId(payment.get().getId());
        pay.setBooking(payment.get().getBooking());
        pay.setStatusPembayaran(true);
        paymentRepository.save(pay);
        return toPaymentResponse(pay);
    }
    //allPayment
    public List<PaymentResponse> getAllPayment (){
        List<Payment> paymentResponses = paymentRepository.findAll();
        return paymentResponses.stream().map(this::toPaymentResponse).toList();
    }
    //paymentByID
    public PaymentResponse getPaymentByID (Integer id){
        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isEmpty()){throw new ApiRequestException(pesan);}
        return toPaymentResponse(payment.get());
    }

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
        response.setIdBooking(payment.getBooking().getId());
        response.setStatusPembayaran(payment.getStatusPembayaran());
        return response;
    }
}
