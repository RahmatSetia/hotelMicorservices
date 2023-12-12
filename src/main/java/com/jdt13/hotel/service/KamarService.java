package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.KamarCheckinRequest;
import com.jdt13.hotel.dto.KamarRequest;
import com.jdt13.hotel.dto.KamarResponse;
import com.jdt13.hotel.entity.Kamar;
import com.jdt13.hotel.exception.ApiRequestException;
import com.jdt13.hotel.repository.KamarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KamarService {

    private final KamarRepository kamarRepository;

    private String pesan = "Kamar tidak di temukan";

    public List<Kamar> getAllKamarBeforeBooking (KamarCheckinRequest kamarCheckinRequest){
        return kamarRepository.findKamarBeforeBookingInCheckinCheckout(kamarCheckinRequest.getCheckin(), kamarCheckinRequest.getCheckout());
    }

    public List<KamarResponse> getAllKamar (){
        List<Kamar> kamars = kamarRepository.findAll();
        return kamars.stream().map(this::toResponseKamar).toList();
    }

    public KamarResponse saveKamar(KamarRequest request){
        Kamar kamar = new Kamar();
        kamar.setNoKamar(request.getNoKamar());
        kamar.setHarga(BigDecimal.valueOf(request.getHarga()));
        kamar.setKategori(request.getKategori());
        kamar.setDeskripsi(request.getDeskripsi());
        kamarRepository.save(kamar);
        return toResponseKamar(kamar);
    }

    public KamarResponse updateKamarById (Integer id, KamarRequest request){
        Optional<Kamar> k = kamarRepository.findById(id);
        if (k.isEmpty()){throw new ApiRequestException(pesan);}
        Kamar kamar = new Kamar();
        kamar.setId(k.get().getId());
        kamar.setNoKamar(request.getNoKamar());
        kamar.setHarga(BigDecimal.valueOf(request.getHarga()));
        kamar.setKategori(request.getKategori());
        kamar.setDeskripsi(request.getDeskripsi());
        kamarRepository.save(kamar);
        return toResponseKamar(kamar);
    }

    public void deleteKamarIdKamar(Integer id){
        Optional<Kamar> kamarId = kamarRepository.findById(id);
        if (kamarId.isEmpty()){throw new ApiRequestException(pesan);}
        kamarRepository.deleteById(id);
    }

    private KamarResponse toResponseKamar (Kamar kamar){
        KamarResponse response = new KamarResponse();
        response.setId(kamar.getId());
        response.setNoKamar(kamar.getNoKamar());
        response.setHarga(kamar.getHarga().doubleValue());
        response.setKategori(kamar.getKategori());
        response.setDeskripsi(kamar.getDeskripsi());
        return response;
    }
}