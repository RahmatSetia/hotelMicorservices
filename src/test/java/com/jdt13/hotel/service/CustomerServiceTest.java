package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.CustomerRequest;
import com.jdt13.hotel.dto.CustomerResponse;
import com.jdt13.hotel.dto.LoginRequest;
import com.jdt13.hotel.dto.LoginResponse;
import com.jdt13.hotel.entity.Customer;
import com.jdt13.hotel.exception.ApiExceptionNotFound;
import com.jdt13.hotel.repository.CustomerRepository;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TokenService tokenService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void resgisterCustomer(){
        CustomerRequest request = new CustomerRequest();
        request.setNama("awang");
        request.setUsername("awang");
        request.setPassword("password");
        request.setAlamat("sidareja");
        request.setPhone("0895339042072");

        Customer cust = new Customer();
        cust.setId(12);
        cust.setNama("awang");
        cust.setUsername("awang");
        cust.setPassword("password");
        cust.setAlamat("sidareja");
        cust.setPhone("0895339042072");
        when(customerRepository.save(any())).thenReturn(cust);

        CustomerResponse response = customerService.registerCustomer(request);
        assertEquals(response.getNama(), request.getNama());
        assertEquals(response.getUsername(), request.getUsername());
        assertEquals(response.getPassword(), request.getPassword());
        assertEquals(response.getAlamat(), request.getAlamat());
        assertEquals(response.getPhone(), request.getPhone());
    }

    @Test
    void LoginTrueCase(){
        Customer cust = new Customer();
        cust.setId(12);
        cust.setNama("awang");
        cust.setUsername("awang");
        cust.setPassword("password");
        cust.setAlamat("sidareja");
        cust.setPhone("0895339042072");

        Customer customer = new Customer();
        customer.setId(12);
        customer.setNama("awang");
        customer.setUsername("awang");
        customer.setPassword("password");
        customer.setAlamat("sidareja");
        customer.setPhone("0895339042072");
        customer.setToken("tokenGenerated");

        LoginRequest request = new LoginRequest();
        when(customerRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword())).thenReturn(Optional.of(cust));
        LoginResponse response = customerService.login(request);

        assertNotNull(response.getToken());
        assertEquals(response.getId(), cust.getId());
    }

    @Test
    void loginFalseCase(){
        String idNotFound = "Customer tidak di temukan";
        LoginRequest request = new LoginRequest();
        when(customerRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword())).thenReturn(Optional.empty());
        ApiExceptionNotFound notFound = assertThrows(ApiExceptionNotFound.class, ()->customerService.login(request));
        assertEquals(idNotFound, notFound.getMessage());
    }

    @Test
    void testGetCustomerWithValidId() {
        Integer validCustomerId = 12345;
        Customer cust = new Customer();
        cust.setId(validCustomerId);
        cust.setNama("awang");
        cust.setUsername("awang");
        cust.setPassword("password");
        cust.setAlamat("sidareja");
        cust.setPhone("0895339042072");

        when(customerRepository.findById(validCustomerId)).thenReturn(Optional.of(cust));
        when(tokenService.getToken(any())).thenReturn(true);
        CustomerResponse customer = customerService.findCustomerById(validCustomerId, "token");
        assertEquals(customer.getId(), validCustomerId);
        assertEquals(customer.getNama(), cust.getNama());
        assertEquals(customer.getUsername(), cust.getUsername());
        assertEquals(customer.getPassword(), cust.getPassword());
        assertEquals(customer.getAlamat(), cust.getAlamat());
        assertEquals(customer.getPhone(), cust.getPhone());
    }

    @Test
    void testGetCustomerWithValidId_TokenFalse() {
        Integer validCustomerId = 12345;
        Customer cust = new Customer();
        cust.setId(validCustomerId);
        String tokenNotFound = "Anda belum login";
        when(customerRepository.findById(validCustomerId)).thenReturn(Optional.of(cust));
        when(tokenService.getToken(any())).thenReturn(false);
        ApiExceptionNotFound notFound = assertThrows(ApiExceptionNotFound.class, () -> customerService.findCustomerById(validCustomerId, "token"));
        assertEquals(notFound.getMessage(), tokenNotFound);
    }

    @Test
    void testGetCustomer_InvalidId() {
        Integer id = 12345;
        Customer cust = new Customer();
        cust.setId(id);
        String tokenNotFound = "Customer tidak di temukan";
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        when(tokenService.getToken(any())).thenReturn(true);
        ApiExceptionNotFound notFound = assertThrows(ApiExceptionNotFound.class, () -> customerService.findCustomerById(id , "token"));
        assertEquals(notFound.getMessage(), tokenNotFound);
    }

    @Test
    void testUpdateCustomerWithValidId_WillReturCustomerUpdated() {
        Integer validCustomerId = 12345;
        Customer cust = new Customer();
        cust.setId(validCustomerId);
        cust.setNama("awang");
        cust.setUsername("awang");
        cust.setPassword("password");
        cust.setAlamat("sidareja");
        cust.setPhone("0895339042072");

        CustomerRequest requestUpdate = new CustomerRequest();
        requestUpdate.setNama("awang Up");
        requestUpdate.setUsername("awang Up");
        requestUpdate.setPassword("password UP");
        requestUpdate.setAlamat("sidareja Up");
        requestUpdate.setPhone("081212341234");

        when(customerRepository.findById(validCustomerId)).thenReturn(Optional.of(cust));
        when(tokenService.getToken(any())).thenReturn(true);
        CustomerResponse customerUpdate = customerService.updateCustomer("token", validCustomerId, requestUpdate);
        assertEquals(customerUpdate.getId(), validCustomerId);
        assertEquals(customerUpdate.getNama(), requestUpdate.getNama());
        assertEquals(customerUpdate.getUsername(), requestUpdate.getUsername());
        assertEquals(customerUpdate.getPassword(), requestUpdate.getPassword());
        assertEquals(customerUpdate.getAlamat(), requestUpdate.getAlamat());
        assertEquals(customerUpdate.getPhone(), requestUpdate.getPhone());
    }

    @Test
    void testUpdateCustomer_InvalidId() {
        Integer id = 12345;
        CustomerRequest cust = new CustomerRequest();
        String tokenNotFound = "Customer tidak di temukan";
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        when(tokenService.getToken(any())).thenReturn(true);
        ApiExceptionNotFound notFound = assertThrows(ApiExceptionNotFound.class, () -> customerService.updateCustomer("token", id, cust));
        assertEquals(notFound.getMessage(), tokenNotFound);
    }

    @Test
    void testUpdateCustomerWithValidId_TokenFalse() {
        Integer validCustomerId = 12345;
        Customer cust = new Customer();
        CustomerRequest request = new CustomerRequest();
        String tokenNotFound = "Anda belum login";
        when(customerRepository.findById(validCustomerId)).thenReturn(Optional.of(cust));
        when(tokenService.getToken(any())).thenReturn(false);
        ApiExceptionNotFound notFound = assertThrows(ApiExceptionNotFound.class, () -> customerService.updateCustomer("token", validCustomerId, request));
        assertEquals(notFound.getMessage(), tokenNotFound);
    }
}
