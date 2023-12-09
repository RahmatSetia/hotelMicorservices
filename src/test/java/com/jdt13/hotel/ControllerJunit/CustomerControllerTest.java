package com.jdt13.hotel.ControllerJunit;

import com.jdt13.hotel.controller.CustomerController;
import com.jdt13.hotel.dto.CustomerRequest;
import com.jdt13.hotel.entity.Customer;
import com.jdt13.hotel.service.CustomerService;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCustomerByIdWithValidId() {
        int validCustomerId = 1;
        Customer mockCustomer = new Customer();
        mockCustomer.setId(validCustomerId);
        when(customerService.findCustomerById(validCustomerId)).thenReturn(mockCustomer);

        ResponseEntity<Customer> responseEntity = customerController.getCustomerById(validCustomerId);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getId(), validCustomerId);
    }

    @Test
    public void testGetCustomerByIdWithInvalidId() {
        int invalidCustomerId = 99; // Assuming this product doesn't exist
        Customer mockCustomer = new Customer();
        mockCustomer.setId(invalidCustomerId);
        when(customerService.findCustomerById(invalidCustomerId)).thenReturn(mockCustomer);

        ResponseEntity<Customer> responseEntity = customerController.getCustomerById(invalidCustomerId);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testUpdateCustomerWithValidData() {
        int validCustomerId = 1;
        Customer mockCustomer = new Customer();
        mockCustomer.setId(validCustomerId); // Assuming this ID exists
        when(customerService.findCustomerById(validCustomerId)).thenReturn(mockCustomer);

        ResponseEntity<Customer> responseEntity = customerController.getCustomerById(validCustomerId);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        assertEquals(responseEntity.getBody().getId(), validCustomerId);
    }

    @Test
    public void testUpdateCustomerWithInvalidData() {
        Customer invalidCustomer = new Customer(); // Missing product ID
        String pesan = "tidak ada customer dengan id ini";

        CustomerRequest req = new CustomerRequest();
        req.setNama(req.getNama());
        when(customerService.updateCustomer(invalidCustomer.getId(), req)).thenThrow();

        ServiceException exception = org.junit.jupiter.api.Assertions.assertThrows(ServiceException.class, () -> {
            customerController.updateCustomerById(invalidCustomer.getId(), req);
        });

        assertEquals(exception.getMessage(), pesan);
    }
}
