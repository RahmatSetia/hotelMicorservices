package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.PinaltiResponse;
import com.jdt13.hotel.entity.Booking;
import com.jdt13.hotel.entity.Kamar;
import com.jdt13.hotel.entity.Pinalti;
import com.jdt13.hotel.entity.Receptionist;
import com.jdt13.hotel.exception.ApiExceptionNotFound;
import com.jdt13.hotel.repository.KamarRepository;
import com.jdt13.hotel.repository.PinaltiRepository;
import com.jdt13.hotel.repository.ReceptionistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PinaltiSeviceTest {
    @InjectMocks
    private PinaltiService pinaltiService;

    @Mock
    private PinaltiRepository pinaltiRepository;

    @Mock
    private ReceptionistRepository receptionistRepository;

    @Mock
    private KamarRepository kamarRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addPinalti_PositivCase(){
        Kamar kamar = new Kamar();
        kamar.setId(1);
        kamar.setNoKamar(12);
        kamar.setHarga(BigDecimal.valueOf(200000));

        Booking booking = new Booking();
        booking.setId(2);
        booking.setKamar(kamar);
        booking.setTotalHarga(BigDecimal.valueOf(200000));

        Receptionist receptionist = new Receptionist();
        receptionist.setId(23);
        receptionist.setNama("awang");

        Date date = new Date(2023, 12, 19, 11,0);
        Pinalti fakePinalti = new Pinalti();
        fakePinalti.setId(2);
        fakePinalti.setBooking(booking);
        fakePinalti.setDateCheckout(date);
        fakePinalti.setReceptionist(receptionist);
        fakePinalti.setDenda(new BigDecimal(200000).multiply(BigDecimal.valueOf(1.5)));

        when(pinaltiRepository.save(any())).thenReturn(fakePinalti);
        when(receptionistRepository.findById(receptionist.getId())).thenReturn(Optional.of(receptionist));
        when(kamarRepository.findById(any())).thenReturn(Optional.of(kamar));
        PinaltiResponse response = pinaltiService.addPinalti(booking, receptionist.getId());

        assertNotNull(response);
        assertEquals(response.getBookingId(), booking.getId());
        assertEquals(response.getReceptionist(), receptionist.getId());
        assertEquals(response.getDenda(), fakePinalti.getDenda());
    }

    @Test
    void getByIdTrueCase(){
        Booking booking = new Booking();
        booking.setId(2);
        booking.setTotalHarga(BigDecimal.valueOf(200000));

        Receptionist receptionist = new Receptionist();
        receptionist.setId(23);
        receptionist.setNama("awang");

        Pinalti fakePinalti = new Pinalti();
        fakePinalti.setId(2);
        fakePinalti.setBooking(booking);
        fakePinalti.setDateCheckout(new Date());
        fakePinalti.setReceptionist(receptionist);
        fakePinalti.setDenda(new BigDecimal(200000).multiply(BigDecimal.valueOf(1.5)));

        when(pinaltiRepository.findById(2)).thenReturn(Optional.of(fakePinalti));
        PinaltiResponse response = pinaltiService.getById(fakePinalti.getId());
        assertEquals(response.getId(), fakePinalti.getId());
        assertEquals(response.getBookingId(), booking.getId());
        assertEquals(response.getDenda(), BigDecimal.valueOf(300000.0));
        assertEquals(response.getReceptionist(), receptionist.getId());
        assertEquals(response.getDateCheckout(), fakePinalti.getDateCheckout());
    }

    @Test
    void getByIdNegativeCase(){
        String pesan = "Id pinalti tidak di temukan";
        when(pinaltiRepository.findById(any())).thenReturn(Optional.empty());
        ApiExceptionNotFound exception = assertThrows(ApiExceptionNotFound.class, ()-> pinaltiService.getById(2));
        assertEquals(exception.getMessage(), pesan);
    }

    @Test
    void getListPenaltiByBokingidTrueCase(){
        Booking booking = new Booking();
        booking.setId(2);
        booking.setTotalHarga(BigDecimal.valueOf(200000));

        Receptionist receptionist = new Receptionist();
        receptionist.setId(23);
        receptionist.setNama("awang");

        Pinalti fakePinalti = new Pinalti();
        fakePinalti.setId(2);
        fakePinalti.setBooking(booking);
        fakePinalti.setDateCheckout(new Date());
        fakePinalti.setReceptionist(receptionist);
        fakePinalti.setDenda(new BigDecimal(200000).multiply(BigDecimal.valueOf(1.5)));

        List<Pinalti> pinaltiList = new ArrayList<>();
        pinaltiList.add(fakePinalti);

        when(pinaltiRepository.bookingId(2)).thenReturn(pinaltiList);
        List<PinaltiResponse> responses = pinaltiService.getByBookingId(2);
        assertNotNull(responses);
        assertEquals(responses.get(0).getId(), fakePinalti.getId());
        assertEquals(responses.get(0).getBookingId(), fakePinalti.getBooking().getId());
        assertEquals(responses.get(0).getDateCheckout(), fakePinalti.getDateCheckout());
        assertEquals(responses.get(0).getReceptionist(), receptionist.getId());
        assertEquals(responses.get(0).getDenda(), fakePinalti.getDenda());
    }

    @Test
    void getListPinaltiByBooking_InvalidId(){
        Integer id = 12;
        String pesan = "Id Booking tidak di temukan";
        ApiExceptionNotFound notFound = new ApiExceptionNotFound(pesan);
        when(pinaltiRepository.bookingId(any())).thenThrow(notFound);
        ApiExceptionNotFound exceptionNotFound = assertThrows(ApiExceptionNotFound.class, ()-> pinaltiService.getByBookingId(id));
        assertEquals(exceptionNotFound.getMessage(), pesan);
    }
}
