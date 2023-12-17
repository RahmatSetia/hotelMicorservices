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

    public KamarResponse bookingKamar(KamarRequest request){

    public List<Kamar> getAllKamarBeforeBooking (KamarCheckinRequest kamarCheckinRequest){
        return kamarRepository.findKamarBeforeBookingInCheckinCheckout();
    }

    public List<KamarResponse> getAllKamar (String token){
        if (!tokenService.getToken(token)){throw new ApiExceptionUnauthorized(tokenNotFound);}
        List<Kamar> kamars = kamarRepository.findAll();
        if (kamars.isEmpty()){throw new ApiExceptionNoContent("Data kamar kosong");}
        return kamars.stream().map(this::toResponseKamar).toList();
    }

    public KamarResponse saveKamar(KamarRequest request){
        Kamar kamar = new Kamar();
        kamar.setNoKamar(request.getNoKamar());
        kamar.setHarga(BigDecimal.valueOf(request.getHarga()));
        kamar.setKategori(request.getKategori());
        kamar.setDeskripsi(request.getDeskripsi());
        kamarRepository.save(kamar);

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