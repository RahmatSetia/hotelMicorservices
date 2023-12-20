package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.BookingRequest;
import com.jdt13.hotel.dto.BookingResponse;
import com.jdt13.hotel.dto.ReportRequest;
import com.jdt13.hotel.entity.Booking;
import com.jdt13.hotel.entity.Customer;
import com.jdt13.hotel.entity.Kamar;
import com.jdt13.hotel.entity.Receptionist;
import com.jdt13.hotel.exception.ApiExceptionNotFound;
import com.jdt13.hotel.exception.ApiExceptionUnauthorized;
import com.jdt13.hotel.exception.ApiRequestException;
import com.jdt13.hotel.repository.BookingRepository;
import com.jdt13.hotel.repository.CustomerRepository;
import com.jdt13.hotel.repository.KamarRepository;
import com.jdt13.hotel.repository.ReceptionistRepository;
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
    private final PaymentService paymentService;
    private final TokenService tokenService;
    private final ReceptionistRepository receptionistRepository;
    private final PinaltiService pinaltiService;

    private String pesan = "Id booking tidak di temukan";
    private String pesanCustomer = "Id customer tidak di temukan";
    private String tokenNotFound = "Anda belum login";
    public BookingResponse addBooking (String token, BookingRequest request){
        if (!tokenService.getToken(token)){throw new ApiExceptionUnauthorized(tokenNotFound);}
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(() -> new ApiExceptionNotFound(pesanCustomer));
        Optional<Kamar> kamar = kamarRepository.findById(request.getKamarId());
        if (kamar.isEmpty()){throw new ApiExceptionNotFound("Id Kamar tidak di temukan");}

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setKamar(kamar.get());
        booking.setTanggalBooking(new Date());
        //keisi ketika chekin
        booking.setCheckin(request.getCheckin());
        //keisi ketika checkout
        booking.setCheckout(request.getCheckout());
        booking.setTotalHarga(kamar.get().getHarga());
        booking.setStatusBooking(false);
        bookingRepository.save(booking);
        paymentService.addPayment(booking);
        return mapToBookingResponse(booking);
    }

    public BookingResponse getBookingById (Integer id){
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isEmpty()){throw new ApiExceptionNotFound(pesan);}
        return mapToBookingResponse(booking.get());
    }

    //reportBookingByCustomerId
    public List<BookingResponse> getBookingByCustomerId (Integer id){
        List<Booking> booking = bookingRepository.customerId(id);
        if (booking.isEmpty()){throw new ApiExceptionNotFound(pesan);}
        return booking.stream().map(this::mapToBookingResponse).toList();
    }

    public List<BookingResponse> getAllBooking (){
        List<Booking> allBooking = bookingRepository.findAll();
        return allBooking.stream().map(this::mapToBookingResponse).toList();
    }

    public String deleteBookingById (Integer id){
        String ok = "berhasil delete Booking dengan idBooking = " + id;
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isEmpty()){throw new ApiExceptionNotFound(pesan);}
        bookingRepository.deleteById(id);
        return ok;
    }

    //accCheckin
    public BookingResponse checkinBooking (Integer id, String tokenRecep){
        if (!tokenService.getTokenReceptionist(tokenRecep)){throw new ApiExceptionUnauthorized(tokenNotFound);}

        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ApiRequestException(pesan));
        if (booking.getStatusBooking().booleanValue()){throw new ApiRequestException("Booking status sudah true");}
        Booking book = new Booking();
        book.setId(id);
        book.setCustomer(booking.getCustomer());
        book.setKamar(booking.getKamar());
        book.setCheckin(booking.getCheckin());
        book.setCheckout(booking.getCheckout());
        book.setTanggalBooking(booking.getTanggalBooking());
        book.setTotalHarga(booking.getTotalHarga());
        book.setStatusBooking(true);
        bookingRepository.save(book);
        return mapToBookingResponse(book);
    }

    //accCheckout
    public BookingResponse checkoutBooking (Integer id, String tokenRecep){
        if (!tokenService.getTokenReceptionist(tokenRecep)){throw new ApiExceptionUnauthorized(tokenNotFound);}
        Receptionist receptionist = receptionistRepository.findByToken(tokenRecep).orElseThrow(() -> new ApiExceptionUnauthorized(tokenNotFound));
        //set jam 1
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new ApiExceptionNotFound(pesan));
        Booking book = new Booking();
        book.setId(id);
        book.setCustomer(booking.getCustomer());
        book.setKamar(booking.getKamar());
        book.setCheckin(booking.getCheckin());
        book.setCheckout(booking.getCheckout());
        book.setTanggalBooking(booking.getTanggalBooking());
        book.setTotalHarga(booking.getTotalHarga());
        book.setStatusBooking(null);
        bookingRepository.save(book);
        pinaltiService.addPinalti(booking, receptionist.getId());
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
