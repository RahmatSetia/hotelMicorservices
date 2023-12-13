package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.*;
import com.jdt13.hotel.entity.Receptionist;
import com.jdt13.hotel.exception.ApiRequestException;
import com.jdt13.hotel.repository.BookingRepository;
import com.jdt13.hotel.repository.ReceptionistRepository;
import com.jdt13.hotel.util.Jwt;
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
        String pesan = "Username dan Password tidak ditemukan";
        if (receptionist.isEmpty()){throw new ApiRequestException(pesan);}
        Receptionist recep = new Receptionist();
        recep.setId(receptionist.get().getId());
        recep.setNama(receptionist.get().getNama());
        recep.setUsername(receptionist.get().getUsername());
        recep.setPassword(receptionist.get().getPassword());
        recep.setToken(Jwt.getToken(request));

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(receptionist.get().getId());
        loginResponse.setToken(recep.getToken());
        return loginResponse;
    }

    public ReceptionistResponse addReceptionist (ReceptionistRequest request){
        Receptionist receptionist = new Receptionist();
        receptionist.setNama(request.getNama());
        receptionist.setUsername(request.getUsername());
        receptionist.setPassword(request.getPassword());
        receptionistRepository.save(receptionist);

        ReceptionistResponse response = new ReceptionistResponse();
        response.setId(receptionist.getId());
        response.setNama(receptionist.getNama());
        response.setUsername(receptionist.getUsername());
        response.setPassword(receptionist.getPassword());
        return response;
    }

    //reporting
}