package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.*;
import com.jdt13.hotel.entity.Booking;
import com.jdt13.hotel.entity.Receptionist;
import com.jdt13.hotel.exception.ApiRequestException;
import com.jdt13.hotel.repository.BookingRepository;
import com.jdt13.hotel.repository.ReceptionistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReceptionistService {

    private final ReceptionistRepository receptionistRepository;

    private final BookingRepository bookingRepository;

    public LoginResponse loginReceptionist(LoginRequest request) {

        Optional<Receptionist> receptionist = receptionistRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        Receptionist r = new Receptionist();
        String pesan = "Username dan Password tidak ditemukan";

        if (receptionist.isEmpty()){
            throw new ApiRequestException(pesan);
        }
        r.setId(r.getId());
        r.setNama(r.getNama());
        r.setUsername(r.getUsername());
        r.setPassword(r.getPassword());
        r.setToken(r.getToken());
        receptionistRepository.save(r);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(request.getUsername());
        loginResponse.setToken(request.getPassword());
        return loginResponse;
    }
}