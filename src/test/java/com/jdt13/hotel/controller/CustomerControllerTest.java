package com.jdt13.hotel.controller;

import com.jdt13.hotel.dto.CustomerResponse;
import com.jdt13.hotel.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCustomerById(){
        CustomerResponse response = new CustomerResponse();
        when(customerService.findCustomerById()).thenReturn();
        ResponseEntity<>
    }
}
