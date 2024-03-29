package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.KamarRequest;
import com.jdt13.hotel.dto.KamarResponse;
import com.jdt13.hotel.entity.Kamar;
import com.jdt13.hotel.exception.ApiExceptionNoContent;
import com.jdt13.hotel.exception.ApiExceptionNotFound;
import com.jdt13.hotel.exception.ApiExceptionUnauthorized;
import com.jdt13.hotel.repository.KamarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KamarService {

    private final KamarRepository kamarRepository;
    private final TokenService tokenService;

    private String pesan = "Kamar tidak di temukan";
    private String tokenNotFound = "Anda belum login";

    public List<KamarResponse> getAllKamar (String token){
        if (!tokenService.getToken(token)){throw new ApiExceptionUnauthorized(tokenNotFound);}
        List<Kamar> kamars = kamarRepository.findAll();
        if (kamars.isEmpty()){throw new ApiExceptionNoContent("Data kamar kosong");}
        return kamars.stream().map(this::toResponseKamar).toList();
    }

    public KamarResponse    saveKamar(KamarRequest request){
        Kamar kamar = new Kamar();
        kamar.setNoKamar(request.getNoKamar());
        kamar.setHarga(request.getHarga());
        kamar.setKategori(request.getKategori());
        kamar.setDeskripsi(request.getDeskripsi());
        kamarRepository.save(kamar);
        return toResponseKamar(kamar);
    }

    public KamarResponse updateKamarById (Integer id, KamarRequest request){
        Optional<Kamar> k = kamarRepository.findById(id);
        if (k.isEmpty()){throw new ApiExceptionNotFound(pesan);}
        Kamar kamar = new Kamar();
        kamar.setId(k.get().getId());
        kamar.setNoKamar(request.getNoKamar());
        kamar.setHarga(request.getHarga());
        kamar.setKategori(request.getKategori());
        kamar.setDeskripsi(request.getDeskripsi());
        kamarRepository.save(kamar);
        return toResponseKamar(kamar);
    }

    public String deleteKamarIdKamar(Integer id){
        String ok = "behasil delete Kamar dengan idBooking = " + id;
        Optional<Kamar> kamarId = kamarRepository.findById(id);
        if (kamarId.isEmpty()){throw new ApiExceptionNotFound(pesan);}
        kamarRepository.deleteById(id);
        return ok;
    }

    private KamarResponse toResponseKamar (Kamar kamar){
        KamarResponse response = new KamarResponse();
        response.setId(kamar.getId());
        response.setNoKamar(kamar.getNoKamar());
        response.setHarga(kamar.getHarga());
        response.setKategori(kamar.getKategori());
        response.setDeskripsi(kamar.getDeskripsi());
        return response;
    }
}