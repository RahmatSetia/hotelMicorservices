package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.BookingRequest;
import com.jdt13.hotel.dto.BookingResponse;
import com.jdt13.hotel.entity.Booking;
import com.jdt13.hotel.entity.Customer;
import com.jdt13.hotel.entity.Kamar;
import com.jdt13.hotel.exception.ApiRequestException;
import com.jdt13.hotel.repository.BookingRepository;
import com.jdt13.hotel.repository.CustomerRepository;
import com.jdt13.hotel.repository.KamarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final KamarRepository kamarRepository;
    private final TokenService tokenService;

    public BookingResponse addBooking (String token, BookingRequest request){
        //id customer dari token sesudah login
        Integer idCust = tokenService.findCustomer(token);
        //logic for harga
        BigDecimal hargaTambahan = new BigDecimal(20000);

        Optional<Customer> customer = customerRepository.findById(request.getCustomerId());
        if (customer.isEmpty()){
            throw new ApiRequestException("Id Customer tidak di temukan");
        }

        Optional<Kamar> kamar = kamarRepository.findById(request.getKamarId());
        if (kamar.isEmpty()){
            throw new ApiRequestException("Id Kamar tidak di temukan");
        }

        Booking booking = new Booking();
        booking.setCustomer(customer.get());
        booking.setKamar(kamar.get());
        booking.setTanggalBooking(request.getTanggalBooking());
        booking.setTotalHarga(kamar.get().getHarga().add(hargaTambahan));
        booking.setStatusBooking(false);
        bookingRepository.save(booking);

        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setCustomerId(booking.getCustomer().getId());
        response.setKamarId(booking.getKamar().getId());
        response.setTanggalBooking(booking.getTanggalBooking());
        response.setTotalHarga(booking.getTotalHarga());
        response.setStatusBooking(booking.getStatusBooking());
        return response;
    }

    public void deleteBookingById (Integer id){
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isEmpty()){throw new ApiRequestException(pesan);}
        return mapToBookingResponse(booking.get());
    }

    //reportBookingByCustomerId
    public List<BookingResponse> getBookingByCustomerId (Integer id){
        List<Booking> booking = bookingRepository.customerId(id);
        if (booking.isEmpty()){throw new ApiRequestException(pesanCustomer);}
        return booking.stream().map(this::mapToBookingResponse).toList();
    }

    public List<BookingResponse> getAllBooking (){
        List<Booking> allBooking = bookingRepository.findAll();
        return allBooking.stream().map(this::mapToBookingResponse).toList();
    }

    public String deleteBookingById (Integer id){
        String ok = "berhasil delete Booking dengan idBooking = " + id;
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isEmpty()){throw new ApiRequestException(pesan);}
        bookingRepository.deleteById(id);
    }
}
