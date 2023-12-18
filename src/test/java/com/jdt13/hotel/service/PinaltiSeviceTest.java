package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.PinaltiResponse;
import com.jdt13.hotel.entity.Booking;
import com.jdt13.hotel.entity.Pinalti;
import com.jdt13.hotel.entity.Receptionist;
import com.jdt13.hotel.repository.PinaltiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PinaltiSeviceTest {
    @InjectMocks
    private PinaltiService pinaltiService;

    @Mock
    private PinaltiRepository pinaltiRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
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
}
