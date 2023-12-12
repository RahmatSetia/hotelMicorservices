package com.jdt13.hotel.service;

import com.jdt13.hotel.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BookingServiceTest {
    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }


}
