package com.jdt13.hotel.controller;

import com.jdt13.hotel.dto.BookingRequest;
import com.jdt13.hotel.dto.BookingResponse;
import com.jdt13.hotel.dto.ReportRequest;
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

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<BookingResponse>> getAllBookingByCustomerId (@PathVariable("id") Integer id){
        List<BookingResponse> getAllById = bookingService.getBookingByCustomerId(id);
        return new ResponseEntity<>(getAllById, HttpStatus.OK);
    }

    @GetMapping("/false")
    public ResponseEntity<List<BookingResponse>> getAllBookingStatusFalse(){
        List<BookingResponse> allBookingFalse = bookingService.allBookingStatusFalse();
        return new ResponseEntity<>(allBookingFalse, HttpStatus.OK);
    }

    @GetMapping("/true")
    public ResponseEntity<List<BookingResponse>> getAllBookingStatusTrue(){
        List<BookingResponse> allBookingFalse = bookingService.allBookingStatusTrue();
        return new ResponseEntity<>(allBookingFalse, HttpStatus.OK);
    }

    @GetMapping("/null")
    public ResponseEntity<List<BookingResponse>> getAllBookingStatusNull(){
        List<BookingResponse> allBookingFalse = bookingService.allBookingStatusNull();
        return new ResponseEntity<>(allBookingFalse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getById (@PathVariable("id") Integer id){
        BookingResponse booking = bookingService.getBookingById(id);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @GetMapping("/report")
    public ResponseEntity<List<BookingResponse>> report (@Valid @RequestBody ReportRequest request){
        List<BookingResponse> response = bookingService.reportByMonth(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookingResponse> addBooking ( @RequestHeader String token, @Valid @RequestBody BookingRequest request){
        BookingResponse response = bookingService.addBooking(token, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("checkin/{id}")
    public ResponseEntity<BookingResponse> checkin (@PathVariable("id") Integer id, @RequestHeader String tokenRecip){
        BookingResponse response = bookingService.checkinBooking(id, tokenRecip);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("checkout/{id}")
    public ResponseEntity<BookingResponse> checkout (@PathVariable("id") Integer id, @RequestHeader String tokenRecip){
        BookingResponse response = bookingService.checkoutBooking(id, tokenRecip);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBookingById(@PathVariable("id") Integer id){
        String response = bookingService.deleteBookingById(id);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}




