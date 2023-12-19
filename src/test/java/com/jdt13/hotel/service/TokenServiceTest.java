package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.LoginRequest;
import com.jdt13.hotel.entity.Customer;
import com.jdt13.hotel.entity.Receptionist;
import com.jdt13.hotel.exception.ApiExceptionNotFound;
import com.jdt13.hotel.exception.ApiRequestException;
import com.jdt13.hotel.repository.CustomerRepository;
import com.jdt13.hotel.repository.ReceptionistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TokenServiceTest {
    @InjectMocks
    private TokenService tokenService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ReceptionistRepository receptionistRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTokenCustomerTrueCase(){
        Customer cust = new Customer();
        when(customerRepository.findByToken("token")).thenReturn(Optional.of(cust));
        Boolean token = tokenService.getToken("token");
        assertTrue(token);
    }

    @Test
    void getTokenFalseCase_ReturnMessage(){
        String pesan = "Token tidak di temukan";
        when(customerRepository.findByToken("token")).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class, ()-> tokenService.getToken("token"));
        assertEquals(exception.getMessage(), pesan);
    }

    @Test
    void getTokenReceptionistTrueCase(){
        Receptionist receptionist = new Receptionist();
        when(receptionistRepository.findByToken("token")).thenReturn(Optional.of(receptionist));
        Boolean token = tokenService.getTokenReceptionist("token");
        assertTrue(token);
    }

    @Test
    void getTokenReceptionistFalseCase_ReturnMessage(){
        String pesan = "Token tidak di temukan";
        when(receptionistRepository.findByToken("token")).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class, ()-> tokenService.getTokenReceptionist("token"));
        assertEquals(exception.getMessage(), pesan);
    }
}
