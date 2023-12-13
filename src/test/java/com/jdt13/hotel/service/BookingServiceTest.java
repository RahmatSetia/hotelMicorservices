package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.BookingRequest;
import com.jdt13.hotel.dto.BookingResponse;
import com.jdt13.hotel.dto.PaymentResponse;
import com.jdt13.hotel.dto.ReportRequest;
import com.jdt13.hotel.entity.Booking;
import com.jdt13.hotel.entity.Customer;
import com.jdt13.hotel.entity.Kamar;
import com.jdt13.hotel.exception.ApiRequestException;
import com.jdt13.hotel.repository.BookingRepository;
import com.jdt13.hotel.repository.CustomerRepository;
import com.jdt13.hotel.repository.KamarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BookingServiceTest {
    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private KamarRepository kamarRepository;

    @Mock
    private PaymentService paymentService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addBoking_PositiveCase(){
        Date date = new Date();
        Integer idCust = 1;
        Integer idKama = 2;
        Integer idBooking = 12;

        BookingRequest request = new BookingRequest();
        request.setCustomerId(idCust);
        request.setKamarId(idKama);
        request.setCheckin(date);
        request.setCheckout(date);

        Customer customer = new Customer();
        customer.setId(idCust);
        customer.setNama("awang");
        customer.setUsername("awang");
        customer.setPassword("awang");
        customer.setAlamat("sidareja");
        customer.setPhone("0895339042072");

        Kamar kamar = new Kamar();
        kamar.setId(idKama);
        kamar.setNoKamar(20);
        kamar.setHarga(BigDecimal.valueOf(200000));
        kamar.setKategori("deluxe");
        kamar.setDeskripsi("deskripsi");

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setIdBooking(idBooking);
        paymentResponse.setStatusPembayaran(false);

        Booking fakeBooking = new Booking();
        fakeBooking.setId(idBooking);
        fakeBooking.setCustomer(customer);
        fakeBooking.setKamar(kamar);
        fakeBooking.setCheckin(date);
        fakeBooking.setCheckout(date);
        fakeBooking.setTotalHarga(kamar.getHarga());
        fakeBooking.setStatusBooking(false);

        when(bookingRepository.save(any())).thenReturn(fakeBooking);
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        when(kamarRepository.findById(any())).thenReturn(Optional.of(kamar));
        when(tokenService.getToken(any())).thenReturn(true);
        when(paymentService.addPayment(any())).thenReturn(paymentResponse);

        BookingResponse response = bookingService.addBooking("token", request);

        assertEquals(response.getCustomerId(), idCust);
    }

    @Test
    void addBoking_InvalidToken(){
        String pesan = "Anda belum login";
        BookingRequest request = new BookingRequest();
        when(tokenService.getToken(any())).thenReturn(false);
        ApiRequestException exception = assertThrows(ApiRequestException.class, ()-> bookingService.addBooking("token", request));
        assertEquals(pesan, exception.getMessage());
    }

    @Test
    void addBoking_CustomerIsEmpety(){
        String pesanCustomer = "Id customer tidak di temukan";
        BookingRequest request = new BookingRequest();
        when(tokenService.getToken("token")).thenReturn(true);
        when(customerRepository.findById(any())).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class, ()-> bookingService.addBooking("token", request));
        assertEquals(pesanCustomer, exception.getMessage());
    }

    @Test
    void addBoking_KamarIsEmpety(){
        String pesan = "Id Kamar tidak di temukan";
        Customer customer = new Customer();
        customer.setId(23);
        customer.setNama("awang");
        customer.setUsername("awang");
        customer.setPassword("awang");
        customer.setAlamat("sidareja");
        customer.setPhone("0895339042072");
        BookingRequest request = new BookingRequest();
        when(tokenService.getToken("token")).thenReturn(true);
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        when(kamarRepository.findById(any())).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class, ()-> bookingService.addBooking("token", request));
        assertEquals(pesan, exception.getMessage());
    }
    //addBookingNegative

    @Test
    void getBookingById_WillReturnBooking(){
        Integer id = 12;

        Date date = new Date();

        Customer customer = new Customer();
        customer.setId(10);
        customer.setNama("awang");
        customer.setUsername("awang");
        customer.setPassword("awang");
        customer.setAlamat("sidareja");
        customer.setPhone("0895339042072");

        Kamar kamar = new Kamar();
        kamar.setId(19);
        kamar.setNoKamar(20);
        kamar.setHarga(BigDecimal.valueOf(200000));
        kamar.setKategori("deluxe");
        kamar.setDeskripsi("deskripsi");

        Booking fakeBooking = new Booking();
        fakeBooking.setId(id);
        fakeBooking.setCustomer(customer);
        fakeBooking.setKamar(kamar);
        fakeBooking.setCheckin(date);
        fakeBooking.setCheckout(date);
        fakeBooking.setTotalHarga(kamar.getHarga());
        fakeBooking.setStatusBooking(false);

        when(bookingRepository.findById(id)).thenReturn(Optional.of(fakeBooking));

        BookingResponse response = bookingService.getBookingById(id);
        assertEquals(response.getId(), id);
        assertEquals(response.getCustomerId(), customer.getId());
        assertEquals(response.getKamarId(), kamar.getId());
        assertEquals(response.getCheckin(), date);
        assertEquals(response.getCheckout(), date);
        assertEquals(response.getTotalHarga(), kamar.getHarga());
        assertFalse(response.getStatusBooking());
    }

    @Test
    void getBookingById_NegativeCase(){
        Integer fakeId = 2;
        String pesan = "Id booking tidak di temukan";
        when(bookingRepository.findById(fakeId)).thenReturn(Optional.empty());

        ApiRequestException exception = assertThrows(ApiRequestException.class,() -> bookingService.getBookingById(fakeId));
        assertEquals(pesan, exception.getMessage());
    }

    @Test
    void getListBookingByCustomerId_WillreturnListBooking(){
        Date date = new Date();
        Customer customer = new Customer();
        customer.setId(10);
        customer.setNama("awang");
        customer.setUsername("awang");
        customer.setPassword("awang");
        customer.setAlamat("sidareja");
        customer.setPhone("0895339042072");

        Kamar kamar = new Kamar();
        kamar.setId(19);
        kamar.setNoKamar(20);
        kamar.setHarga(BigDecimal.valueOf(200000));
        kamar.setKategori("deluxe");
        kamar.setDeskripsi("deskripsi");

        Booking fakeBooking = new Booking();
        fakeBooking.setId(23);
        fakeBooking.setCustomer(customer);
        fakeBooking.setKamar(kamar);
        fakeBooking.setCheckin(date);
        fakeBooking.setCheckout(date);
        fakeBooking.setTotalHarga(kamar.getHarga());
        fakeBooking.setStatusBooking(false);

        Booking booking2 = new Booking();
        booking2.setId(24);

        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(fakeBooking);

        when(bookingRepository.customerId(customer.getId())).thenReturn(bookingList);
        List<BookingResponse> response = bookingService.getBookingByCustomerId(customer.getId());
        assertEquals(response.get(0).getId(), bookingList.get(0).getId());
        assertEquals(response.get(0).getCustomerId(), bookingList.get(0).getCustomer().getId());
        assertEquals(response.get(0).getKamarId(), bookingList.get(0).getKamar().getId());
        assertEquals(response.get(0).getCheckin(), bookingList.get(0).getCheckin());
        assertEquals(response.get(0).getCheckout(), bookingList.get(0).getCheckout());
        assertEquals(response.get(0).getTotalHarga(), bookingList.get(0).getTotalHarga());
        assertEquals(response.get(0).getStatusBooking(), bookingList.get(0).getStatusBooking());
    }

    @Test
    void getListBookingByCustomerId_NegativeCase(){
        String pesanCustomer = "Id customer tidak di temukan";
        Integer id = 2;
        ApiRequestException apiRequestException = new ApiRequestException(pesanCustomer);

        when(bookingRepository.customerId(id)).thenThrow(apiRequestException);

        ApiRequestException exception = assertThrows(ApiRequestException.class, ()-> bookingService.getBookingByCustomerId(id ));
        assertEquals(pesanCustomer, exception.getMessage());
    }

    @Test
    void getAllBooking(){
        Integer id = 23;

        Date date = new Date();
        Customer customer = new Customer();
        customer.setId(10);
        customer.setNama("awang");
        customer.setUsername("awang");
        customer.setPassword("awang");
        customer.setAlamat("sidareja");
        customer.setPhone("0895339042072");

        Kamar kamar = new Kamar();
        kamar.setId(19);
        kamar.setNoKamar(20);
        kamar.setHarga(BigDecimal.valueOf(200000));
        kamar.setKategori("deluxe");
        kamar.setDeskripsi("deskripsi");

        Booking fakeBooking = new Booking();
        fakeBooking.setId(id);
        fakeBooking.setCustomer(customer);
        fakeBooking.setKamar(kamar);
        fakeBooking.setCheckin(date);
        fakeBooking.setCheckout(date);
        fakeBooking.setTotalHarga(kamar.getHarga());
        fakeBooking.setStatusBooking(false);

        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(fakeBooking);

        when(bookingRepository.findAll()).thenReturn(bookingList);

        List<BookingResponse> res = bookingService.getAllBooking();

        assertEquals(res.get(0).getId(), bookingList.get(0).getId());
    }

    //deleteById
    //cehekin
    @Test
    void CheckinBooking_NegativeBookingEmpety(){
        Integer fakeId = 132;
        String pesan = "Id booking tidak di temukan";

        when(bookingRepository.findById(any())).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,() -> bookingService.getBookingById(fakeId));
        assertEquals(exception.getMessage(), pesan);
    }

    @Test
    void CheckinBooking_NegativeBookingStatusAlreadyTrue(){
        Integer id = 12;
        Date date = new Date();

        Customer customer = new Customer();
        customer.setId(10);
        customer.setNama("awang");
        customer.setUsername("awang");
        customer.setPassword("awang");
        customer.setAlamat("sidareja");
        customer.setPhone("0895339042072");

        Kamar kamar = new Kamar();
        kamar.setId(19);
        kamar.setNoKamar(20);
        kamar.setHarga(BigDecimal.valueOf(200000));
        kamar.setKategori("deluxe");
        kamar.setDeskripsi("deskripsi");

        Booking fakeBooking = new Booking();
        fakeBooking.setId(id);
        fakeBooking.setCustomer(customer);
        fakeBooking.setKamar(kamar);
        fakeBooking.setCheckin(date);
        fakeBooking.setCheckout(date);
        fakeBooking.setTotalHarga(kamar.getHarga());
        fakeBooking.setStatusBooking(true);
        String pesan = "Booking status sudah true";

        when(bookingRepository.findById(any())).thenReturn(Optional.of(fakeBooking));
        ApiRequestException exception = assertThrows(ApiRequestException.class,() -> bookingService.checkinBooking(id));
        assertEquals(exception.getMessage(), pesan);
    }

    @Test
    void checkinBooking_ReturnBookingStatusTrue(){
        Integer id = 12;
        Date date = new Date();

        Customer customer = new Customer();
        customer.setId(10);
        customer.setNama("awang");
        customer.setUsername("awang");
        customer.setPassword("awang");
        customer.setAlamat("sidareja");
        customer.setPhone("0895339042072");

        Kamar kamar = new Kamar();
        kamar.setId(19);
        kamar.setNoKamar(20);
        kamar.setHarga(BigDecimal.valueOf(200000));
        kamar.setKategori("deluxe");
        kamar.setDeskripsi("deskripsi");

        Booking fakeBooking = new Booking();
        fakeBooking.setId(id);
        fakeBooking.setCustomer(customer);
        fakeBooking.setKamar(kamar);
        fakeBooking.setCheckin(date);
        fakeBooking.setCheckout(date);
        fakeBooking.setTotalHarga(kamar.getHarga());
        fakeBooking.setStatusBooking(false);

        when(bookingRepository.findById(id)).thenReturn(Optional.of(fakeBooking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(fakeBooking);
        BookingResponse response = bookingService.checkinBooking(id);
        assertTrue(response.getStatusBooking());
    }

    @Test
    void CheckoutBooking_returnBookingStatusNull(){
        Integer id = 12;

        Date date = new Date();

        Customer customer = new Customer();
        customer.setId(10);
        customer.setNama("awang");
        customer.setUsername("awang");
        customer.setPassword("awang");
        customer.setAlamat("sidareja");
        customer.setPhone("0895339042072");

        Kamar kamar = new Kamar();
        kamar.setId(19);
        kamar.setNoKamar(20);
        kamar.setHarga(BigDecimal.valueOf(200000));
        kamar.setKategori("deluxe");
        kamar.setDeskripsi("deskripsi");

        Booking fakeBooking = new Booking();
        fakeBooking.setId(id);
        fakeBooking.setCustomer(customer);
        fakeBooking.setKamar(kamar);
        fakeBooking.setCheckin(date);
        fakeBooking.setCheckout(date);
        fakeBooking.setTotalHarga(kamar.getHarga());
        fakeBooking.setStatusBooking(false);

        when(bookingRepository.findById(id)).thenReturn(Optional.of(fakeBooking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(fakeBooking);
        BookingResponse response = bookingService.checkoutBooking(id);
        assertNull(response.getStatusBooking());
    }

    @Test
    void CheckoutBooking_returnPesanNegative(){
        Integer fakeId = 132;
        String pesan = "Id booking tidak di temukan";

        when(bookingRepository.findById(any())).thenReturn(Optional.empty());
        ApiRequestException exception = assertThrows(ApiRequestException.class,() -> bookingService.checkoutBooking(fakeId));
        assertEquals(exception.getMessage(), pesan);
    }

    @Test
    void getAllBookingStatusFalse(){
        Integer id = 23;

        Date date = new Date();

        Customer customer = new Customer();
        customer.setId(10);
        customer.setNama("awang");
        customer.setUsername("awang");
        customer.setPassword("awang");
        customer.setAlamat("sidareja");
        customer.setPhone("0895339042072");

        Kamar kamar = new Kamar();
        kamar.setId(19);
        kamar.setNoKamar(20);
        kamar.setHarga(BigDecimal.valueOf(200000));
        kamar.setKategori("deluxe");
        kamar.setDeskripsi("deskripsi");

        Booking fakeBooking = new Booking();
        fakeBooking.setId(id);
        fakeBooking.setCustomer(customer);
        fakeBooking.setKamar(kamar);
        fakeBooking.setCheckin(date);
        fakeBooking.setCheckout(date);
        fakeBooking.setTotalHarga(kamar.getHarga());
        fakeBooking.setStatusBooking(false);

        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(fakeBooking);
        when(bookingRepository.statusBookingFalse()).thenReturn(bookingList);
        List<BookingResponse> res = bookingService.allBookingStatusFalse();
        assertFalse(res.get(0).getStatusBooking());
    }

    @Test
    void getAllBookingStatusTrue(){
        Integer id = 23;

        Date date = new Date();

        Customer customer = new Customer();
        customer.setId(10);
        customer.setNama("awang");
        customer.setUsername("awang");
        customer.setPassword("awang");
        customer.setAlamat("sidareja");
        customer.setPhone("0895339042072");

        Kamar kamar = new Kamar();
        kamar.setId(19);
        kamar.setNoKamar(20);
        kamar.setHarga(BigDecimal.valueOf(200000));
        kamar.setKategori("deluxe");
        kamar.setDeskripsi("deskripsi");

        Booking fakeBooking = new Booking();
        fakeBooking.setId(id);
        fakeBooking.setCustomer(customer);
        fakeBooking.setKamar(kamar);
        fakeBooking.setCheckin(date);
        fakeBooking.setCheckout(date);
        fakeBooking.setTotalHarga(kamar.getHarga());
        fakeBooking.setStatusBooking(true);

        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(fakeBooking);
        when(bookingRepository.statusBookingTrue()).thenReturn(bookingList);
        List<BookingResponse> res = bookingService.allBookingStatusTrue();
        assertTrue(res.get(0).getStatusBooking());
    }

    @Test
    void getAllBookingStatusNull(){
        Integer id = 23;

        Date date = new Date();

        Customer customer = new Customer();
        customer.setId(10);
        customer.setNama("awang");
        customer.setUsername("awang");
        customer.setPassword("awang");
        customer.setAlamat("sidareja");
        customer.setPhone("0895339042072");

        Kamar kamar = new Kamar();
        kamar.setId(19);
        kamar.setNoKamar(20);
        kamar.setHarga(BigDecimal.valueOf(200000));
        kamar.setKategori("deluxe");
        kamar.setDeskripsi("deskripsi");

        Booking fakeBooking = new Booking();
        fakeBooking.setId(id);
        fakeBooking.setCustomer(customer);
        fakeBooking.setKamar(kamar);
        fakeBooking.setCheckin(date);
        fakeBooking.setCheckout(date);
        fakeBooking.setTotalHarga(kamar.getHarga());
        fakeBooking.setStatusBooking(null);

        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(fakeBooking);
        when(bookingRepository.statusBookingNull()).thenReturn(bookingList);
        List<BookingResponse> res = bookingService.allBookingStatusNull();
        assertNull(res.get(0).getStatusBooking());
    }

    @Test
    void reportBookingByMonth(){
        Date date = new Date();

        Customer customer = new Customer();
        customer.setId(10);
        customer.setNama("awang");
        customer.setUsername("awang");
        customer.setPassword("awang");
        customer.setAlamat("sidareja");
        customer.setPhone("0895339042072");

        Kamar kamar = new Kamar();
        kamar.setId(19);
        kamar.setNoKamar(20);
        kamar.setHarga(BigDecimal.valueOf(200000));
        kamar.setKategori("deluxe");
        kamar.setDeskripsi("deskripsi");

        Booking fakeBooking = new Booking();
        fakeBooking.setId(12);
        fakeBooking.setCustomer(customer);
        fakeBooking.setKamar(kamar);
        fakeBooking.setCheckin(date);
        fakeBooking.setCheckout(date);
        fakeBooking.setTotalHarga(kamar.getHarga());
        fakeBooking.setStatusBooking(false);

        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(fakeBooking);

        ReportRequest request = new ReportRequest();
        request.setStartDay(new Date());
        request.setEndDay(new Date());

        when(bookingRepository.reportByDate(request.getStartDay(), request.getEndDay())).thenReturn(bookingList);

        List<BookingResponse> res = bookingService.reportByMonth(request);
        assertEquals(res.get(0).getId(), bookingList.get(0).getId());
    }
}
