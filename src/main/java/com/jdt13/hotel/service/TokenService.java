package com.jdt13.hotel.service;

import com.jdt13.hotel.entity.Customer;
import com.jdt13.hotel.exception.ApiRequestException;
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
        if (getToken.isEmpty()){
            throw new ApiRequestException("Token tidak di temukan");
        }
        return getToken.isPresent();
    }

    public Integer findCustomer(String token){
        Optional<Customer> getCustomer = customerRepository.findByToken(token);
        if (getCustomer.isEmpty()){
            throw new ApiRequestException("Token tidak di temukan");
        }
        return getCustomer.get().getId();
    }
}
