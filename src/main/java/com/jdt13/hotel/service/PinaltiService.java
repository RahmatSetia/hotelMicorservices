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
import java.time.LocalTime;
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
        LocalTime now = LocalTime.now();
        LocalTime batas = LocalTime.of(13, 0);
        Receptionist receptionist = receptionistRepository.findById(recep).orElseThrow(() -> new ApiExceptionNotFound("Id Receptionist tidak di temukan"));
        Kamar kamar = kamarRepository.findById(booking.getKamar().getId()).orElseThrow(() -> new ApiExceptionNotFound("Id Kamar tidak di temukan"));
        BigDecimal harga = kamar.getHarga();
        if (now.isAfter(batas)){
            harga = kamar.getHarga().multiply(BigDecimal.valueOf(1.5));
        }
        Pinalti pinalti = new Pinalti();
        pinalti.setBooking(booking);
        pinalti.setDateCheckout(new Date());
        pinalti.setReceptionist(receptionist);
        pinalti.setDenda(harga);
        pinaltiRepository.save(pinalti);
        return mapToPinaltiResponse(pinalti);
    }

    public List<PinaltiResponse> getAllPinalti(){
        List<Pinalti> pinalti = pinaltiRepository.findAll();
        return pinalti.stream().map(this::mapToPinaltiResponse).toList();
    }

    public PinaltiResponse getById (Integer id){
        Pinalti pinalti = pinaltiRepository.findById(id).orElseThrow(()->new ApiExceptionNotFound("Id pinalti tidak di temukan"));
        return mapToPinaltiResponse(pinalti);
    }

    public List<PinaltiResponse> getByBookingId (Integer idBooking){
        List<Pinalti> pinalti = pinaltiRepository.bookingId(idBooking);
        if (pinalti.isEmpty()){throw new ApiExceptionNotFound("Id Booking tidak di temukan");}
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
