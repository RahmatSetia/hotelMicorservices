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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final KamarRepository kamarRepository;
    private final PaymentService paymentService;
    private final TokenService tokenService;

    public BookingResponse addBooking (BookingRequest request){
        DateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date now = new Date();
//        String token = "token";
//        Customer idCust = tokenService.findCustomer(token);

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
        booking.setTanggalBooking(new Date());
        booking.setCheckin(request.getCheckin());
        booking.setCheckout(request.getCheckout());
        booking.setTotalHarga(kamar.get().getHarga());
        booking.setStatusBooking(false);
        bookingRepository.save(booking);
        paymentService.addPayment(booking);
        return mapToBookingResponse(booking);
    }

    public BookingResponse getBookingById (Integer id){
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isEmpty()){
            throw new ApiRequestException("Id Booking tidak di temukan");
        }
        return mapToBookingResponse(booking.get());
    }

    public List<BookingResponse> getAllBooking (){
        List<Booking> allBooking = bookingRepository.findAll();
        return allBooking.stream().map(this::mapToBookingResponse).toList();
    }

    public void deleteBookingById (Integer id){
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isEmpty()){
            throw new ApiRequestException("Id Booking tidak di temukan");
        }
        bookingRepository.deleteById(id);
    }
    private BookingResponse mapToBookingResponse(Booking booking) {
        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setId(booking.getId());
        bookingResponse.setCustomerId(booking.getCustomer().getId());
        bookingResponse.setKamarId(booking.getKamar().getId());
        bookingResponse.setCheckin(booking.getCheckin());
        bookingResponse.setCheckout(booking.getCheckout());
        bookingResponse.setTotalHarga(booking.getTotalHarga());
        bookingResponse.setStatusBooking(booking.getStatusBooking());
        return bookingResponse;
    }
}
