package com.jdt13.hotel.controller;

import com.jdt13.hotel.dto.BookingRequest;
import com.jdt13.hotel.dto.BookingResponse;
import com.jdt13.hotel.dto.ReportRequest;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
    void testReport(){
        Date start = new Date(2023,12,1);
        Date end = new Date(2023,12,30);

        Date checkin = new Date(2023,12,2);
        Date checkout = new Date(2023,12,3);

        Date checkin1 = new Date(2023,12,4);
        Date checkout1 = new Date(2023,12,5);

        ReportRequest request = new ReportRequest();
        request.setStartDay(start);
        request.setEndDay(end);

        BookingResponse response = new BookingResponse();
        response.setId(1);
        response.setCustomerId(2);
        response.setKamarId(2);
        response.setCheckin(checkin);
        response.setCheckout(checkout);
        response.setTotalHarga(BigDecimal.valueOf(200000));
        response.setStatusBooking(true);

        BookingResponse response2 = new BookingResponse();
        response2.setId(1);
        response2.setCustomerId(2);
        response2.setKamarId(2);
        response2.setCheckin(checkin1);
        response2.setCheckout(checkout1);
        response2.setTotalHarga(BigDecimal.valueOf(200000));
        response2.setStatusBooking(true);

        List<BookingResponse> responseList = new ArrayList<>();
        responseList.add(response);
        responseList.add(response2);

        when(bookingService.reportByMonth(request)).thenReturn(responseList);
        ResponseEntity<List<BookingResponse>> test = bookingController.report(request);

        assertEquals(HttpStatus.OK, test.getStatusCode());
        assertEquals(test.getBody(), responseList);
    }

    @Test
    void testAddBooking(){
        String token = "token";

        Date checkin = new Date(2023,12,2);
        Date checkout = new Date(2023,12,3);

        BookingRequest request = new BookingRequest();
        request.setCustomerId(2);
        request.setKamarId(32);
        request.setCheckin(checkin);
        request.setCheckout(checkout);

        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setId(2);
        bookingResponse.setKamarId(32);
        bookingResponse.setCustomerId(2);
        bookingResponse.setCheckin(checkin);
        bookingResponse.setCheckout(checkout);
        bookingResponse.setTotalHarga(BigDecimal.valueOf(200000));
        bookingResponse.setStatusBooking(true);

        when(bookingService.addBooking(token, request)).thenReturn(bookingResponse);
        ResponseEntity<BookingResponse> response = bookingController.addBooking(token, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookingResponse, response.getBody());
    }

    @Test
    void testChekin(){
        Integer id = 2;
        String token = "tokenReceptionist";

        Date checkin = new Date(2023,12,2);
        Date checkout = new Date(2023,12,3);

        BookingRequest request = new BookingRequest();
        request.setCustomerId(2);
        request.setKamarId(32);
        request.setCheckin(checkin);
        request.setCheckout(checkout);

        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setId(2);
        bookingResponse.setKamarId(32);
        bookingResponse.setCustomerId(2);
        bookingResponse.setCheckin(checkin);
        bookingResponse.setCheckout(checkout);
        bookingResponse.setTotalHarga(BigDecimal.valueOf(200000));
        bookingResponse.setStatusBooking(true);
        when(bookingService.checkinBooking(id, token)).thenReturn(bookingResponse);
        ResponseEntity<BookingResponse> responseEntity = bookingController.checkin(id, token);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(bookingResponse, responseEntity.getBody());
    }

    @Test
    void testChekout(){
        Integer id = 2;
        String token = "tokenReceptionist";

        Date checkin = new Date(2023,12,2);
        Date checkout = new Date(2023,12,3);

        BookingRequest request = new BookingRequest();
        request.setCustomerId(2);
        request.setKamarId(32);
        request.setCheckin(checkin);
        request.setCheckout(checkout);

        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setId(2);
        bookingResponse.setKamarId(32);
        bookingResponse.setCustomerId(2);
        bookingResponse.setCheckin(checkin);
        bookingResponse.setCheckout(checkout);
        bookingResponse.setTotalHarga(BigDecimal.valueOf(200000));
        bookingResponse.setStatusBooking(true);
        when(bookingService.checkoutBooking(id, token)).thenReturn(bookingResponse);
        ResponseEntity<BookingResponse> responseEntity = bookingController.checkout(id, token);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(bookingResponse, responseEntity.getBody());
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
