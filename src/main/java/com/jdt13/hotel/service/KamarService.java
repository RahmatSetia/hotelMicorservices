package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.KamarRequest;
import com.jdt13.hotel.dto.KamarResponse;
import com.jdt13.hotel.entity.Kamar;
import com.jdt13.hotel.repository.KamarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KamarService {

    private final KamarRepository kamarRepository;

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

    public KamarResponse updateKamarIdKamar(KamarRequest request){

        Optional<Kamar> kamar = kamarRepository.findByNoKamar(request.getNoKamar());

        if (kamar.isEmpty()){
            throw new IllegalArgumentException("Kamar tidak ditemukan");
        }
            KamarResponse response = new KamarResponse();
            response.setNoKamar(request.getNoKamar());
            return response;
    }

    public String deleteKamarIdKamar(Integer id){

        Optional<Kamar> kamarId = kamarRepository.findById(id);

        String pesan = "";

        if (kamarId.isEmpty()){
            throw new IllegalArgumentException("Kamar sudah di booking");
        }
        kamarRepository.deleteById(id);
        return pesan;
    }
}