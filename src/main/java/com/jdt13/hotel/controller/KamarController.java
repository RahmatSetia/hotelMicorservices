package com.jdt13.hotel.controller;

import com.jdt13.hotel.dto.KamarCheckinRequest;
import com.jdt13.hotel.dto.KamarRequest;
import com.jdt13.hotel.dto.KamarResponse;
import com.jdt13.hotel.entity.Kamar;
import com.jdt13.hotel.service.KamarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel/kamar")
@RequiredArgsConstructor
@Validated
public class KamarController {
    private final KamarService kamarService;

    @GetMapping("/all")
    public ResponseEntity<List<KamarResponse>> getAllKamar(@RequestHeader String token){
        List<KamarResponse> kamars = kamarService.getAllKamar(token);
        return new ResponseEntity<>(kamars, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Kamar>> getKamar(@Valid @RequestBody KamarCheckinRequest request){
        List<Kamar> kamars = kamarService.getAllKamarBeforeBooking(request);
        return new ResponseEntity<>(kamars, HttpStatus.OK);
    }
    //saveKamar
    @PostMapping
    public ResponseEntity<KamarResponse> postKamar(@Valid @RequestBody KamarRequest request){
        KamarResponse response = kamarService.saveKamar(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    //updateKamar
    @PutMapping("/{id}")
    public ResponseEntity<KamarResponse> updateKamar (@PathVariable("id") Integer id, @Valid @RequestBody KamarRequest request){
        KamarResponse response = kamarService.updateKamarById(id, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    //deleteKamatById
    @DeleteMapping("/{id}")
    public ResponseEntity<KamarResponse> deleteKamarById (@PathVariable("id") Integer id){
        kamarService.deleteKamarIdKamar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
