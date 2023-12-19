package com.jdt13.hotel.controller;

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
        CustomerResponse mockCustomer = new CustomerResponse();
        when(customerService.findCustomerById(customerId, token)).thenReturn(mockCustomer);

        // Act
        ResponseEntity<CustomerResponse> responseEntity = customerController.getCustomerById(token, customerId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockCustomer, responseEntity.getBody());
        verify(customerService, times(1)).findCustomerById(customerId);
    }

    void testGetCustomerById(){
        CustomerResponse response = new CustomerResponse();
        when(customerService.findCustomerById()).thenReturn();
        ResponseEntity<>
    }

    @Test
    void testRegisterCustomer() {
        // Arrange
        CustomerRequest customerRequest = new CustomerRequest();
        CustomerResponse mockResponse = new CustomerResponse();
        when(customerService.registerCustomer(customerRequest)).thenReturn(mockResponse);

        // Act
        ResponseEntity<CustomerResponse> responseEntity = customerController.registerCustomer(customerRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(mockResponse, responseEntity.getBody());
        verify(customerService, times(1)).registerCustomer(customerRequest);
    }

    @Test
    void testLogin() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        LoginResponse mockResponse = new LoginResponse();
        when(customerService.login(loginRequest)).thenReturn(mockResponse);

        // Act
        ResponseEntity<LoginResponse> responseEntity = customerController.login(loginRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockResponse, responseEntity.getBody());
        verify(customerService, times(1)).login(loginRequest);
    }

    @Test
    void testUpdateCustomerById() {
        // Arrange
        int customerId = 1;
        CustomerRequest customerRequest = new CustomerRequest();
        CustomerResponse mockResponse = new CustomerResponse();
        when(customerService.updateCustomer(customerId, customerRequest)).thenReturn(mockResponse);

        // Act
        ResponseEntity<CustomerResponse> responseEntity = customerController.updateCustomerById(customerId, customerRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockResponse, responseEntity.getBody());
        verify(customerService, times(1)).updateCustomer(customerId, customerRequest);
    }

    @Test
    void testDeleteById() {
        // Arrange
        int customerId = 1;

        // Act
        customerController.deleteById(customerId);

        // Assert
        verify(customerService, times(1)).deleteCustomerById(customerId);
    }
}
