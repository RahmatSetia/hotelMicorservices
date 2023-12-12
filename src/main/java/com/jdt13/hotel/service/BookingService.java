package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.BookingRequest;
import com.jdt13.hotel.dto.BookingResponse;
import com.jdt13.hotel.dto.ReportRequest;
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

    private String pesan = "Id booking tidak di temukan";
    private String tokenNotFound = "Anda belum login";
    public BookingResponse addBooking (String token, BookingRequest request){
        if (!tokenService.getToken(token)){throw new ApiRequestException(tokenNotFound);}
        Optional<Customer> customer = customerRepository.findById(request.getCustomerId());
        if (customer.isEmpty()){throw new ApiRequestException("Id Customer tidak di temukan");}
        Optional<Kamar> kamar = kamarRepository.findById(request.getKamarId());
        if (kamar.isEmpty()){throw new ApiRequestException("Id Kamar tidak di temukan");}

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
        if (booking.isEmpty()){throw new ApiRequestException(pesan);}
        return mapToBookingResponse(booking.get());
    }

    //reportBookingByCustomerId
    public List<BookingResponse> getBookingByCustomerId (Integer id){
        List<Booking> booking = bookingRepository.customerId(id);
        if (booking.isEmpty()){throw new ApiRequestException(pesan);}
        return booking.stream().map(this::mapToBookingResponse).toList();
    }

    public List<BookingResponse> getAllBooking (){
        List<Booking> allBooking = bookingRepository.findAll();
        return allBooking.stream().map(this::mapToBookingResponse).toList();
    }

    public void deleteBookingById (Integer id){
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isEmpty()){throw new ApiRequestException(pesan);}
        bookingRepository.deleteById(id);
    }

    //accCheckin
    public BookingResponse checkinBooking (Integer id){
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isEmpty()){throw new ApiRequestException(pesan);}
        if (booking.get().getStatusBooking().booleanValue()){throw new ApiRequestException("Booking status sudah true");}
        Booking book = new Booking();
        book.setId(id);
        book.setCustomer(book.getCustomer());
        book.setKamar(book.getKamar());
        book.setCheckin(book.getCheckin());
        book.setCheckout(book.getCheckout());
        book.setTanggalBooking(book.getTanggalBooking());
        book.setTotalHarga(book.getTotalHarga());
        book.setStatusBooking(true);
        bookingRepository.save(book);
        return mapToBookingResponse(book);
    }

    //accCheckout
    public BookingResponse checkoutBooking (Integer id){
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isEmpty()){throw new ApiRequestException(pesan);}
        Booking book = new Booking();
        book.setId(id);
        book.setCustomer(book.getCustomer());
        book.setKamar(book.getKamar());
        book.setCheckin(book.getCheckin());
        book.setCheckout(book.getCheckout());
        book.setTanggalBooking(book.getTanggalBooking());
        book.setTotalHarga(book.getTotalHarga());
        book.setStatusBooking(null);
        bookingRepository.save(book);
        return mapToBookingResponse(book);
    }

    public List<BookingResponse> allBookingStatusFalse(){
        List<Booking> booking = bookingRepository.statusBookingFalse();
        return booking.stream().map(this::mapToBookingResponse).toList();
    }
    public List<BookingResponse> allBookingStatusTrue(){
        List<Booking> booking = bookingRepository.statusBookingTrue();
        return booking.stream().map(this::mapToBookingResponse).toList();
    }
    public List<BookingResponse> allBookingStatusNull(){
        List<Booking> booking = bookingRepository.statusBookingNull();
        return booking.stream().map(this::mapToBookingResponse).toList();
    }
    public List<BookingResponse> reportByMonth(ReportRequest request){
        List<Booking> bookings = bookingRepository.reportByDate(request.getStartDay(), request.getEndDay());
        return bookings.stream().map(this::mapToBookingResponse).toList();
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
