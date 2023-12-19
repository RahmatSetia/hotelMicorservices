package com.jdt13.hotel.controller;

import com.jdt13.hotel.dto.CustomerRequest;
import com.jdt13.hotel.dto.CustomerResponse;
import com.jdt13.hotel.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
    void testGetCustomerById() {
        // Arrange
        String token = "token 1";
        int customerId = 1;
        CustomerResponse response = new CustomerResponse();
        response.setId(customerId);
        response.setNama("fulan");
        response.setPassword("test123");
        response.setUsername("user");
        response.setPhone("085846564");
        response.setAlamat("jl sedayu group");


        when(customerService.findCustomerById(customerId, token)).thenReturn(response);

        // Act
        ResponseEntity<CustomerResponse> responseEntity = customerController.getCustomerById(token, customerId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
        verify(customerService, times(1)).findCustomerById(customerId, token);
    }

    @Test
    void testRegisterCustomer() {
        // Arrange
        Integer id = 1;
        CustomerRequest request = new CustomerRequest();
        request.setNama("Fulan");
        request.setPassword("test123");
        request.setUsername("user2");
        request.setPhone("045445454");
        request.setAlamat("jl jl indivara");

        CustomerResponse response = new CustomerResponse();
        response.setId(id);
        response.setNama(request.getNama());
        response.setPassword(request.getPassword());
        response.setUsername(request.getUsername());
        response.setPhone(request.getPhone());
        response.setAlamat(request.getAlamat());

        when(customerService.registerCustomer(request)).thenReturn(response);

        // Act
        ResponseEntity<CustomerResponse> responseEntity = customerController.registerCustomer(request);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
        verify(customerService, times(1)).registerCustomer(request);
    }
}
