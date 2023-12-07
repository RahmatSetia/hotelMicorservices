package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.KamarRequest;
import com.jdt13.hotel.dto.KamarResponse;
import com.jdt13.hotel.entity.Kamar;
import com.jdt13.hotel.exception.ApiRequestException;
import com.jdt13.hotel.repository.KamarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KamarService {

    private final KamarRepository kamarRepository;

    public List<Kamar> getAllKamarBeforeBooking (Date dateIn, Date dateOut){
        return kamarRepository.findKamarBeforeBookingInCheckinCheckout(dateIn, dateOut);
    }

    public KamarResponse saveKamar(KamarRequest request){
        Kamar kamar = new Kamar();
        kamar.setNoKamar(request.getNoKamar());
        kamar.setHarga(BigDecimal.valueOf(request.getHarga()));
        kamar.setKategori(request.getKategori());
        kamar.setDeskripsi(request.getDeskripsi());
        kamarRepository.save(kamar);

        KamarResponse response = new KamarResponse();
        response.setNoKamar(kamar.getNoKamar());
        response.setHarga(kamar.getHarga().doubleValue());
        response.setKategori(kamar.getKategori());
        response.setDeskripsi(kamar.getDeskripsi());
        return response;
    }

    public KamarResponse updateKamarById (Integer id, KamarRequest request){
        String pesan = "Kamar tidak di temukan";
        Optional<Kamar> k = kamarRepository.findById(id);
        if (k.isEmpty()){
            throw new ApiRequestException(pesan);
        }
        Kamar kamar = new Kamar();
        kamar.setId(k.get().getId());
        kamar.setNoKamar(request.getNoKamar());
        kamar.setHarga(BigDecimal.valueOf(request.getHarga()));
        kamar.setKategori(request.getKategori());
        kamar.setDeskripsi(request.getDeskripsi());
        kamarRepository.save(kamar);

        KamarResponse response = new KamarResponse();
        response.setId(kamar.getId());
        response.setNoKamar(request.getNoKamar());
        response.setHarga(request.getHarga());
        response.setKategori(request.getKategori());
        response.setDeskripsi(request.getDeskripsi());
        return response;
    }

    public String deleteKamarIdKamar(Integer id){
        Optional<Kamar> kamarId = kamarRepository.findById(id);
        String pesan = "Id kamar tidak di temukan";
        if (kamarId.isEmpty()){
            throw new ApiRequestException(pesan);
        }
        kamarRepository.deleteById(id);
        return pesan;
    }
}