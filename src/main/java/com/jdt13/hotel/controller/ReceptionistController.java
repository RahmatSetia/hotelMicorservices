package com.jdt13.hotel.controller;

import com.jdt13.hotel.service.ReceptionistService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequiredArgsConstructor
@Validated

@RequestMapping("/hotel/receptionist")
public class ReceptionistController {

    private final ReceptionistService receptionistService;
}
