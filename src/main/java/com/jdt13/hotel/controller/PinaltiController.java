package com.jdt13.hotel.controller;

import com.jdt13.hotel.dto.PinaltiResponse;
import com.jdt13.hotel.service.PinaltiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("hotel/pinalti")
@RequiredArgsConstructor
public class PinaltiController {
    private final PinaltiService pinaltiService;

    @GetMapping
    public ResponseEntity<List<PinaltiResponse>> getAllPinalti (){
        List<PinaltiResponse> responses = pinaltiService.getAllPinalti();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PinaltiResponse> getPinalti (@PathVariable("id") Integer id){
        PinaltiResponse responses = pinaltiService.getById(id);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("booking/{id}")
    public ResponseEntity<List<PinaltiResponse>> getPinaltiByBookingId (@PathVariable("id") Integer id){
        List<PinaltiResponse> responses = pinaltiService.getByBookingId(id);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
