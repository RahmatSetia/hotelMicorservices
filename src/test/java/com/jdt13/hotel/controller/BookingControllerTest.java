package com.jdt13.hotel.controller;

import com.jdt13.hotel.dto.BookingResponse;
import com.jdt13.hotel.entity.Booking;
import com.jdt13.hotel.exception.ApiRequestException;
import com.jdt13.hotel.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BookingControllerTest {
    @InjectMocks
    private BookingController bookingController;

    @Mock
    private BookingService bookingService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooking(){
        BookingResponse response = new BookingResponse();
        response.setId(1);
        response.setKamarId(2);
        List<BookingResponse> bookingList = new ArrayList<>();
        bookingList.add(response);
        when(bookingService.getAllBooking()).thenReturn(bookingList);
        ResponseEntity<List<BookingResponse>> resController = bookingController.getAllBooking();

        assertEquals(HttpStatus.OK, resController.getStatusCode());
        assertEquals(resController.getBody(), bookingList);
    }

    @Test
    void testGetAllBookingByCustomerId(){
        Integer id = 2;
        BookingResponse response = new BookingResponse();
        response.setId(1);
        response.setCustomerId(id);
        response.setKamarId(2);
        List<BookingResponse> bookingList = new ArrayList<>();
        bookingList.add(response);
        when(bookingService.getBookingByCustomerId(2)).thenReturn(bookingList);
        ResponseEntity<List<BookingResponse>> resController = bookingController.getAllBookingByCustomerId(2);

        assertEquals(HttpStatus.OK, resController.getStatusCode());
        assertEquals(resController.getBody(), bookingList);
        assertEquals(resController.getBody().get(0).getCustomerId(), id);
    }

    @Test
    void testGetAllBookingStatusFalse(){
        BookingResponse response = new BookingResponse();
        response.setId(1);
        response.setKamarId(2);
        response.setStatusBooking(false);
        List<BookingResponse> bookingList = new ArrayList<>();
        bookingList.add(response);
        when(bookingService.allBookingStatusFalse()).thenReturn(bookingList);
        ResponseEntity<List<BookingResponse>> responseEntity = bookingController.getAllBookingStatusFalse();

        assertFalse(responseEntity.getBody().get(0).getStatusBooking());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody(), bookingList);
    }

    @Test
    void testGetAllBookingStatusTrue(){
        BookingResponse response = new BookingResponse();
        response.setId(1);
        response.setKamarId(2);
        response.setStatusBooking(true);
        List<BookingResponse> bookingList = new ArrayList<>();
        bookingList.add(response);
        when(bookingService.allBookingStatusTrue()).thenReturn(bookingList);
        ResponseEntity<List<BookingResponse>> responseEntity = bookingController.getAllBookingStatusTrue();

        assertTrue(responseEntity.getBody().get(0).getStatusBooking());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody(), bookingList);
    }

    @Test
    void testGetAllBookingStatusNull(){
        BookingResponse response = new BookingResponse();
        response.setId(1);
        response.setKamarId(2);
        response.setStatusBooking(null);
        List<BookingResponse> bookingList = new ArrayList<>();
        bookingList.add(response);
        when(bookingService.allBookingStatusNull()).thenReturn(bookingList);
        ResponseEntity<List<BookingResponse>> responseEntity = bookingController.getAllBookingStatusNull();

        assertNull(responseEntity.getBody().get(0).getStatusBooking());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody(), bookingList);
    }

    @Test
    void testGetBookingById(){
        Integer id = 2;
        BookingResponse response = new BookingResponse();
        response.setId(id);
        response.setKamarId(2);
        response.setStatusBooking(false);
        when(bookingService.getBookingById(id)).thenReturn(response);
        ResponseEntity<BookingResponse> responseEntity = bookingController.getById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody().getId(), id);
    }

    @Test
    void testDeleteBookingById(){
        Integer id = 1;
        String response = "behasil delete Booking dengan idBooking = " + id;
        when(bookingService.deleteBookingById(id)).thenReturn(response);
        ResponseEntity<String> responseEntity = bookingController.deleteBookingById(id);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody(), response);
    }

}
