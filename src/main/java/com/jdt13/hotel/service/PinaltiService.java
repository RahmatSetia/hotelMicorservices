package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.PinaltiResponse;
import com.jdt13.hotel.entity.Booking;
import com.jdt13.hotel.entity.Kamar;
import com.jdt13.hotel.entity.Pinalti;
import com.jdt13.hotel.entity.Receptionist;
import com.jdt13.hotel.exception.ApiExceptionNotFound;
import com.jdt13.hotel.repository.BookingRepository;
import com.jdt13.hotel.repository.KamarRepository;
import com.jdt13.hotel.repository.PinaltiRepository;
import com.jdt13.hotel.repository.ReceptionistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PinaltiService {
    private final PinaltiRepository pinaltiRepository;
    private final BookingRepository bookingRepository;
    private final ReceptionistRepository receptionistRepository;
    private final KamarRepository kamarRepository;

    public PinaltiResponse addPinalti (Booking booking, Integer recep){
        Date now = new Date();
        Receptionist receptionist = receptionistRepository.findById(recep).orElseThrow(() -> new ApiExceptionNotFound("Id Receptionist tidak di temukan"));
        Kamar kamar = kamarRepository.findById(booking.getKamar().getId()).orElseThrow(() -> new ApiExceptionNotFound("Id Kamar tidak di temukan"));
        Pinalti pinalti = new Pinalti();
        pinalti.setBooking(booking);
        pinalti.setDateCheckout(now);
        pinalti.setReceptionist(receptionist);
        pinalti.setDenda(kamar.getHarga().multiply(BigDecimal.valueOf(1.5)));
        pinaltiRepository.save(pinalti);
        return mapToPinaltiResponse(pinalti);
    }

    public PinaltiResponse getById (Integer id){
        Pinalti pinalti = pinaltiRepository.findById(id).orElseThrow(()->new ApiExceptionNotFound("Id pinalti tidak di temukan"));
        return mapToPinaltiResponse(pinalti);
    }

    public List<PinaltiResponse> getByBookingId (Integer idBooking){
        List<Pinalti> pinalti = pinaltiRepository.bookingId(idBooking);
        return pinalti.stream().map(this::mapToPinaltiResponse).toList();
    }
    private PinaltiResponse mapToPinaltiResponse(Pinalti pinalti){
        PinaltiResponse response = new PinaltiResponse();
        response.setId(pinalti.getId());
        response.setBookingId(pinalti.getBooking().getId());
        response.setDateCheckout(pinalti.getDateCheckout());
        response.setReceptionist(pinalti.getReceptionist().getId());
        response.setDenda(pinalti.getDenda());
        return response;
    }
}
