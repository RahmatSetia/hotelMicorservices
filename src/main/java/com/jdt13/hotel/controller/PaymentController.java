package com.jdt13.hotel.controller;

import com.jdt13.hotel.dto.PaymentRequest;
import com.jdt13.hotel.dto.PaymentResponse;
import com.jdt13.hotel.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated

@RequestMapping("/customer/payment")
public class PaymentController {

    private final PaymentService paymentService;

//    @PostMapping
//    public ResponseEntity<PaymentResponse> paymentCustomer (@Valid @RequestBody PaymentRequest paymentRequest){
//        PaymentResponse response = paymentService.paymentResponse(paymentRequest);
//        return  new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
}
