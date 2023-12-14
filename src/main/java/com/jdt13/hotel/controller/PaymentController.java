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

    //accPayment
    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponse> accPayment (@PathVariable("id") Integer id){
        PaymentResponse response = paymentService.accPayment(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //getListAllPayment
    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAllPament (){
        List<PaymentResponse> response = paymentService.getAllPayment();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById (@PathVariable("id") Integer id){
        PaymentResponse response = paymentService.getPaymentByID(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePaymentById (@PathVariable("id") Integer id){
        String response = paymentService.deletePaymentById(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
