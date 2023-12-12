package com.jdt13.hotel.ServiceJunit;

import com.jdt13.hotel.dto.CustomerRequest;
import com.jdt13.hotel.dto.CustomerResponse;
import com.jdt13.hotel.entity.Customer;
import com.jdt13.hotel.exception.ApiRequestException;
import com.jdt13.hotel.repository.CustomerRepository;
import com.jdt13.hotel.service.CustomerService;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCustomerWithValidId() {

        Integer validCustomerId = 12345;
        Customer cust = new Customer();
        cust.setId(validCustomerId);

        when(customerRepository.findById(validCustomerId)).thenReturn(Optional.of(cust));
        CustomerResponse customer = customerService.findCustomerById(validCustomerId);

        assertEquals(customer.getId(), validCustomerId);
    }

    @Test
    public void testGetCustomerWithInvalidId() {

        Integer invalidCustomerId = 99999; // Assuming this product doesn't exist
        Customer customer = new Customer();
        customer.setId(invalidCustomerId);
//        ServiceException exception = assertThrows(ServiceException.class, () -> {
//            customerRepository.findById(invalidCustomerId);
//        });

//        assertEquals(response.getId(), exception);
//        assertEquals(exception.getMessage(), "Tidak ada customer dengan ID ini");
        assertThrows(ApiRequestException.class, () -> {
            customerService.findCustomerById(invalidCustomerId);
        });
    }

    @Test
    public void testUpdateCustomerWithValidData() {

        Customer existingCustomer = new Customer();
        existingCustomer.setId(12345);
        existingCustomer.setNama("asdfgh");

        CustomerRequest request = new CustomerRequest();
        request.setNama("Updated Customer Name");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(12345);
        updatedCustomer.setNama("Updated Customer Name");

        when(customerRepository.findById(existingCustomer.getId())).thenReturn(Optional.of(existingCustomer));

        CustomerResponse result = customerService.updateCustomer(existingCustomer.getId(), request);

        assertEquals(result.getId(), existingCustomer.getId());
        assertEquals(result.getNama(), updatedCustomer.getNama());
    }

    @Test
    public void testUpdateCustomerWithInvalidId() {
        Customer invalidCustomer = new Customer();
        String pesan = "Customer tidak ditemukan, jadi tidak ada yang dapat diubah.";

        CustomerRequest req = new CustomerRequest();
        req.setNama(req.getNama());
        invalidCustomer.setId(99999); // Assuming this product doesn't exist

        ServiceException exception = assertThrows(ServiceException.class, () -> {
            customerRepository.findById(invalidCustomer.getId());
        });

        assertEquals(exception.getMessage(), pesan);
    }
}
