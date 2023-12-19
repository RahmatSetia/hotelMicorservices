package com.jdt13.hotel.controller;

import com.jdt13.hotel.dto.KamarRequest;
import com.jdt13.hotel.dto.KamarResponse;
import com.jdt13.hotel.service.KamarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class KamarControllerTest {

    @InjectMocks
    private KamarController kamarController;

    @Mock
    private KamarService kamarService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllKamar(){
        KamarResponse response = new KamarResponse();
        response.setId(1);
        response.setNoKamar(2);
        List<KamarResponse> kamarList = new ArrayList<>();
        kamarList.add(response);
        when(kamarService.getAllKamar("token")).thenReturn(kamarList);
        ResponseEntity<List<KamarResponse>> resController = kamarController.getAllKamar("token");

        assertEquals(HttpStatus.OK, resController.getStatusCode());
        assertEquals(resController.getBody(), kamarList);
    }

    @Test
    void testPostKamar(){
        Integer id = 3;
        KamarRequest request = new KamarRequest();
        request.setNoKamar(id);
        request.setHarga(BigDecimal.valueOf(2));
        request.setDeskripsi("ini kamar");
        request.setKategori("ini kategori");

        KamarResponse response = new KamarResponse();
        response.setId(id);
        response.setNoKamar(id);
        response.setHarga(BigDecimal.valueOf(2));
        response.setDeskripsi("ini kamar");
        response.setKategori("ini kategori");
        when(kamarService.saveKamar(request)).thenReturn(response);
        ResponseEntity<KamarResponse> resController = kamarController.postKamar(request);

        assertEquals(HttpStatus.CREATED, resController.getStatusCode());
        assertEquals(resController.getBody(), response);
    }

    @Test
    void testUpdateKamar(){
        KamarResponse response = new KamarResponse();
        KamarRequest request = new KamarRequest();
        request.setNoKamar(1);
        request.setHarga(BigDecimal.valueOf(2));
        request.setDeskripsi("ini kamar");
        request.setKategori("ini kategori");
        when(kamarService.updateKamarById(1, request)).thenReturn(response);
        ResponseEntity<KamarResponse> resController = kamarController.updateKamar();

        assertEquals(HttpStatus.NO_CONTENT, resController.getStatusCode());
        assertEquals(resController.getBody(),response);
    }
}
