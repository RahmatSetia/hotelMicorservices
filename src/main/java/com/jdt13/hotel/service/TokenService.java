package com.jdt13.hotel.service;

import com.jdt13.hotel.entity.Customer;
import com.jdt13.hotel.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final CustomerRepository customerRepository;

    public boolean getToken(String token){
        Optional<Customer> getToken = customerRepository.findByToken(token);
        return getToken.isPresent();
    }
}
