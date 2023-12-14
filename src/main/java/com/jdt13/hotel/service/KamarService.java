package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.KamarRequest;
import com.jdt13.hotel.dto.KamarResponse;
import com.jdt13.hotel.entity.Kamar;
import com.jdt13.hotel.exception.ApiRequestException;
import com.jdt13.hotel.repository.KamarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KamarService {

    private final KamarRepository kamarRepository;

    public KamarResponse bookingKamar(KamarRequest request){

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
        Kamar k = new Kamar();
        String pesan = "Kamar tidak ditemukan";

        if (kamar.isEmpty()){
            throw new ApiRequestException(pesan);
        }
        k.setId(k.getId());
        k.setNoKamar(k.getNoKamar());
        k.setHarga(k.getHarga());
        k.setKategori(k.getKategori());
        k.setDeskripsi(k.getDeskripsi());
        kamarRepository.save(k);

        KamarResponse response = new KamarResponse();
        response.setNoKamar(k.getNoKamar());
        return response;
    }

    public void deleteKamarIdKamar(Integer id){

        Optional<Kamar> kamarId = kamarRepository.findById(id);
        String pesan = "Kamar tidak ditemukan";

        if (kamarId.isEmpty()){
            throw new ApiRequestException(pesan);
        }
        kamarRepository.deleteById(id);
    }
}