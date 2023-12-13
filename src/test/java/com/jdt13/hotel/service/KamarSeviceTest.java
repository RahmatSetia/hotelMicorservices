package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.KamarRequest;
import com.jdt13.hotel.dto.KamarResponse;
import com.jdt13.hotel.entity.Kamar;
import com.jdt13.hotel.exception.ApiRequestException;
import com.jdt13.hotel.repository.KamarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
class KamarSeviceTest {
    @InjectMocks
    private KamarService kamarService;

    @Mock
    private KamarRepository kamarRepository;

    @Mock
    private TokenService tokenService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllKamarAsList_PsotiveCase(){
        Kamar kamar = new Kamar();
        kamar.setId(1);
        kamar.setNoKamar(12);
        kamar.setDeskripsi("deskripsi");
        kamar.setKategori("deluxe");
        kamar.setHarga(BigDecimal.valueOf(200000));
        kamar.setStatus(false);

        String token = "Token";

        List<Kamar> kamarList = new ArrayList<>();
        kamarList.add(kamar);
        when(tokenService.getToken(token)).thenReturn(true);
        when(kamarRepository.findAll()).thenReturn(kamarList);
        List<KamarResponse> response = kamarService.getAllKamar(token);
        assertEquals(response.get(0).getId(), kamarList.get(0).getId());
        assertEquals(response.get(0).getNoKamar(), kamarList.get(0).getNoKamar());
        assertEquals(response.get(0).getDeskripsi(), kamarList.get(0).getDeskripsi());
        assertEquals(response.get(0).getKategori(), kamarList.get(0).getKategori());
        assertEquals(response.get(0).getHarga(), kamarList.get(0).getHarga());
    }

    @Test
    void getAllKamarAsListInvalidToken(){
        String token = "token";
        String tokenNotFound = "Anda belum login";
        Kamar kamar = new Kamar();
        List<Kamar> kamarList = new ArrayList<>();
        kamarList.add(kamar);
        when(tokenService.getToken(token)).thenReturn(false);
        when(kamarRepository.findAll()).thenReturn(kamarList);
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> kamarService.getAllKamar(token));
        assertEquals(exception.getMessage(), tokenNotFound);
    }

    @Test
    void saveKamarTrueCase(){
        Kamar kamar = new Kamar();
        kamar.setId(1);
        kamar.setNoKamar(12);
        kamar.setDeskripsi("deskripsi");
        kamar.setKategori("deluxe");
        kamar.setHarga(BigDecimal.valueOf(200000));
        kamar.setStatus(false);

        KamarRequest request = new KamarRequest();
        request.setNoKamar(kamar.getNoKamar());
        request.setDeskripsi(kamar.getDeskripsi());
        request.setHarga(kamar.getHarga());
        request.setKategori(kamar.getKategori());

        when(kamarRepository.save(kamar)).thenReturn(kamar);
        KamarResponse response = kamarService.saveKamar(request);
        assertEquals(response.getNoKamar(), kamar.getNoKamar());
        assertEquals(response.getHarga(), kamar.getHarga());
        assertEquals(response.getDeskripsi(), kamar.getDeskripsi());
        assertEquals(response.getKategori(), kamar.getKategori());
    }

    @Test
    void updateKamarById_TrueCase(){
        Integer id = 1;
        Kamar kamar = new Kamar();
        kamar.setId(1);
        kamar.setNoKamar(12);
        kamar.setDeskripsi("deskripsi");
        kamar.setKategori("deluxe");
        kamar.setHarga(BigDecimal.valueOf(200000));
        kamar.setStatus(false);

        KamarRequest request = new KamarRequest();
        request.setNoKamar(23);
        request.setDeskripsi("update deskripsi");
        request.setHarga(BigDecimal.valueOf(190000));
        request.setKategori("update kategori");

        when(kamarRepository.findById(id)).thenReturn(Optional.of(kamar));
        when(kamarRepository.save(any())).thenAnswer(updatUser -> {
            Kamar kamarSave = updatUser.getArgument(0);
            kamarSave.setNoKamar(request.getNoKamar());
            kamarSave.setId(id);
            kamarSave.setDeskripsi(request.getDeskripsi());
            kamarSave.setKategori(request.getKategori());
            kamarSave.setHarga(request.getHarga());
            return kamarSave;
        });
        KamarResponse response = kamarService.updateKamarById(id, request);
        assertEquals(response.getId(), id);
        assertEquals(response.getNoKamar(), request.getNoKamar());
        assertEquals(response.getHarga(), request.getHarga());
        assertEquals(response.getDeskripsi(), request.getDeskripsi());
        assertEquals(response.getKategori(), request.getKategori());
    }

    @Test
    void updateKamarById_NegativeCase(){
        Integer fakeId = 2;
        String pesan = "Kamar tidak di temukan";
        KamarRequest request = new KamarRequest();
        when(kamarRepository.findById(any())).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> kamarService.updateKamarById(fakeId, request));
        assertEquals(pesan, exception.getMessage());
    }

    @Test
    void deleteKamarById_PositiveCase(){
        Integer id = 2;
        String ok = "behasil delete Kamar dengan idBooking = " + id;
        Kamar kamar = new Kamar();
        when(kamarRepository.findById(id)).thenReturn(Optional.of(kamar));
        String response = kamarService.deleteKamarIdKamar(id);
        assertEquals(response, ok);
    }

    @Test
    void deleteKamarById_NegativeCase(){
        Integer id = 2;
        String pesan = "Kamar tidak di temukan";
        Kamar kamar = new Kamar();
        when(kamarRepository.findById(id)).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> kamarService.deleteKamarIdKamar(id));
        assertEquals(exception.getMessage(), pesan);
    }

}
