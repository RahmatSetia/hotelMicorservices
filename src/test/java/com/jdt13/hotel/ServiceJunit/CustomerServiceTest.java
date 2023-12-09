package com.jdt13.hotel.ServiceJunit;

import com.jdt13.hotel.dto.CustomerRequest;
import com.jdt13.hotel.dto.CustomerResponse;
import com.jdt13.hotel.entity.Customer;
import com.jdt13.hotel.service.CustomerService;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerServiceTest {

    private CustomerService customerService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCustomerWithValidId() {
        Integer validCustomerId = 12345;
        Optional<Customer> customer = customerService.findCustomerById(validCustomerId);

        assertTrue(customer.isPresent());
        assertEquals(customer.get().getId(), validCustomerId);
    }

    @Test
    public void testGetCustomerWithInvalidId() {
        Integer invalidCustomerId = 99999; // Assuming this product doesn't exist
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            customerService.findCustomerById(invalidCustomerId);
        });
        String pesan = "Tidak ada customer dengan ID ini";
        assertEquals(exception.getMessage(), pesan);
    }

    @Test
    public void testUpdateCustomerWithValidData() {
        Customer existingCustomer = new Customer();
        existingCustomer.setId(12345);
        existingCustomer.setNama("asdfgh");

        CustomerRequest updatedCustomer = new CustomerRequest();
        updatedCustomer.setNama("Updated Customer Name");

        CustomerResponse result = customerService.updateCustomer(existingCustomer.getId(), updatedCustomer);

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
            customerService.updateCustomer(invalidCustomer.getId(), req);
        });

        assertEquals(exception.getMessage(), pesan);
    }
}
