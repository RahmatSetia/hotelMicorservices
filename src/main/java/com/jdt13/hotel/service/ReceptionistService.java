package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.LoginRequest;
import com.jdt13.hotel.dto.LoginResponse;
import com.jdt13.hotel.entity.Receptionist;
import com.jdt13.hotel.repository.ReceptionistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReceptionistService {

    private final ReceptionistRepository receptionistRepository;

    public LoginResponse loginReceptionist(LoginRequest request) {

        Optional<Receptionist> receptionist = receptionistRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());

        if (receptionist.isEmpty()){
            throw new IllegalArgumentException("Username dan Password tidak ditemukan");
        }

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(request.getUsername());
        loginResponse.setToken(request.getPassword());
        return loginResponse;
    }

    public
}
