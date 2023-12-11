package com.jdt13.hotel.controller;

import com.jdt13.hotel.dto.BookingRequest;
import com.jdt13.hotel.dto.BookingResponse;
import com.jdt13.hotel.entity.Booking;
import com.jdt13.hotel.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel/booking")
@RequiredArgsConstructor
@Validated
public class BookingController {
    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBooking (){
        List<BookingResponse> getAll = bookingService.getAllBooking();
        return new ResponseEntity<>(getAll, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getById (@PathVariable("id") Integer id){
        BookingResponse booking = bookingService.getBookingById(id);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookingResponse> addBooking (@Valid @RequestBody BookingRequest request){
        BookingResponse response = bookingService.addBooking(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BookingResponse> deleteBookingById(@PathVariable("id") Integer id){
        bookingService.deleteBookingById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}




