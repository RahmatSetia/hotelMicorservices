package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.*;
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
import static org.mockito.Mockito.*;

class BookingServiceTest {
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

    @Mock
    private PinaltiService pinaltiService;

    @Mock
    private ReceptionistRepository receptionistRepository;

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
        ApiExceptionUnauthorized exception = assertThrows(ApiExceptionUnauthorized.class, ()-> bookingService.addBooking("token", request));
        assertEquals(pesan, exception.getMessage());
    }

    @Test
    void addBoking_CustomerIsEmpety(){
        String pesanCustomer = "Id customer tidak di temukan";
        BookingRequest request = new BookingRequest();
        when(tokenService.getToken("token")).thenReturn(true);
        when(customerRepository.findById(any())).thenReturn(Optional.empty());
        ApiExceptionNotFound exception = assertThrows(ApiExceptionNotFound.class, ()-> bookingService.addBooking("token", request));
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
        ApiExceptionNotFound exception = assertThrows(ApiExceptionNotFound.class, ()-> bookingService.addBooking("token", request));
        assertEquals(pesan, exception.getMessage());
    }

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

        ApiExceptionNotFound exception = assertThrows(ApiExceptionNotFound.class,() -> bookingService.getBookingById(fakeId));
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
        ApiExceptionNotFound apiRequestException = new ApiExceptionNotFound(pesanCustomer);
        when(bookingRepository.customerId(id)).thenThrow(apiRequestException);
        ApiExceptionNotFound exception = assertThrows(ApiExceptionNotFound.class, ()-> bookingService.getBookingByCustomerId(id));
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
    @Test
    void deleteById_PositiveCase(){
        Integer id = 1;
        Booking booking = new Booking();
        String ok = "berhasil delete Booking dengan idBooking = " + id;
        when(bookingRepository.findById(id)).thenReturn(Optional.of(booking));
        String responseDelete = bookingService.deleteBookingById(id);
        assertEquals(ok, responseDelete);
    }

    @Test
    void deleteById_NegativeCase(){
        Integer id = 1;
        String pesan = "Id booking tidak di temukan";
        when(bookingRepository.findById(id)).thenReturn(Optional.empty());
        ApiExceptionNotFound exception = assertThrows(ApiExceptionNotFound.class, () -> bookingService.deleteBookingById(id));
        assertEquals(pesan, exception.getMessage());
    }

    @Test
    void CheckinBooking_NegativeBookingEmpty(){
        Integer fakeId = 132;
        String pesan = "Id booking tidak di temukan";
        when(bookingRepository.findById(any())).thenReturn(Optional.empty());
        ApiExceptionNotFound exception = assertThrows(ApiExceptionNotFound.class,() -> bookingService.getBookingById(fakeId));
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
        when(tokenService.getTokenReceptionist(any())).thenReturn(true);
        ApiRequestException exception = assertThrows(ApiRequestException.class,() -> bookingService.checkinBooking(id, "token"));
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
        when(tokenService.getTokenReceptionist(any())).thenReturn(true);
        BookingResponse response = bookingService.checkinBooking(id, "token");
        assertTrue(response.getStatusBooking());
    }

    @Test
    void CheckoutBooking_returnBookingStatusNull(){
        Integer id = 12;

        Date date = new Date();

        Receptionist receptionist = new Receptionist();
        receptionist.setId(21);
        receptionist.setNama("nama");
        receptionist.setUsername("username");
        receptionist.setPassword("password");
        receptionist.setToken("token");

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

        PinaltiResponse pinaltiResponse = new PinaltiResponse();
        pinaltiResponse.setId(2);
        pinaltiResponse.setBookingId(fakeBooking.getId());
        pinaltiResponse.setReceptionist(receptionist.getId());
        pinaltiResponse.setDenda(kamar.getHarga().multiply(BigDecimal.valueOf(1.5)));
        pinaltiResponse.setDateCheckout(new Date());

        when(bookingRepository.findById(id)).thenReturn(Optional.of(fakeBooking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(fakeBooking);
        when(receptionistRepository.findByToken("token")).thenReturn(Optional.of(receptionist));
        when(tokenService.getTokenReceptionist("token")).thenReturn(true);
        when(pinaltiService.addPinalti(fakeBooking, receptionist.getId())).thenReturn(pinaltiResponse);
        BookingResponse response = bookingService.checkoutBooking(id, "token");
        assertNull(response.getStatusBooking());
    }

    @Test
    void CheckoutBooking_returnPesanNegative(){
        Receptionist receptionist = new Receptionist();
        receptionist.setId(21);
        receptionist.setNama("awang");
        receptionist.setToken("token");

        Integer fakeId = 132;
        String pesan = "Anda belum login";
        when(bookingRepository.findById(any())).thenReturn(Optional.empty());
        when(receptionistRepository.findByToken(any())).thenReturn(Optional.of(receptionist));
        ApiExceptionUnauthorized exception = assertThrows(ApiExceptionUnauthorized.class,() -> bookingService.checkoutBooking(fakeId, "token"));
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
