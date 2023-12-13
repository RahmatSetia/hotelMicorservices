package com.jdt13.hotel.controller;

import com.jdt13.hotel.dto.CustomerRequest;
import com.jdt13.hotel.dto.CustomerResponse;
import com.jdt13.hotel.dto.LoginRequest;
import com.jdt13.hotel.dto.LoginResponse;
import com.jdt13.hotel.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotel/customer")
@RequiredArgsConstructor
@Validated
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById (@RequestHeader String token ,@PathVariable("id") Integer id){
        CustomerResponse response = customerService.findCustomerById(id, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> registerCustomer (@Valid @RequestBody CustomerRequest customerRequest){
        CustomerResponse response = customerService.registerCustomer(customerRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@Valid @RequestBody LoginRequest request){
        LoginResponse response = customerService.login(request);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomerById(@RequestHeader String token, @PathVariable("id") Integer id, @Valid @RequestBody CustomerRequest request){
        CustomerResponse response = customerService.updateCustomer(token, id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        customerService.deleteCustomerById(id);
    }

}
