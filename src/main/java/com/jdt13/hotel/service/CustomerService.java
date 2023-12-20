package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.*;
import com.jdt13.hotel.entity.Customer;
import com.jdt13.hotel.exception.ApiExceptionNotFound;
import com.jdt13.hotel.repository.CustomerRepository;
import com.jdt13.hotel.util.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final TokenService tokenService;

    private String idNotFound = "Customer tidak di temukan";
    private String tokenNotFound = "Anda belum login";
    public CustomerResponse registerCustomer (CustomerRequest request){
        Customer customer = new Customer();
        customer.setNama(request.getNama());
        customer.setUsername(request.getUsername());
        customer.setPassword(request.getPassword());
        customer.setAlamat(request.getAlamat());
        customer.setPhone(request.getPhone());
        customerRepository.save(customer);
        return toCustomerResponse(customer);
    }

    public LoginResponse login (LoginRequest request){
        Customer customer = customerRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword()).orElseThrow(()-> new ApiExceptionNotFound(idNotFound));
        Customer c = new Customer();
        c.setId(customer.getId());
        c.setNama(customer.getNama());
        c.setUsername(customer.getUsername());
        c.setPassword(customer.getPassword());
        c.setAlamat(customer.getAlamat());
        c.setPhone(customer.getPhone());
        c.setToken(Jwt.getToken(request));
        customerRepository.save(c);

        LoginResponse response = new LoginResponse();
        response.setId(c.getId());
        response.setToken(c.getToken());
        return response;
    }

    public CustomerResponse findCustomerById (Integer id, String token){
        if (!tokenService.getToken(token)){throw new ApiExceptionNotFound(tokenNotFound);}
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ApiExceptionNotFound(idNotFound));
        return toCustomerResponse(customer);
    }

    public CustomerResponse updateCustomer (String token, Integer id, CustomerRequest request){
        if (!tokenService.getToken(token)){throw new ApiExceptionNotFound(tokenNotFound);}
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ApiExceptionNotFound(idNotFound));
        Customer c = new Customer();
        c.setId(customer.getId());
        c.setNama(request.getNama());
        c.setUsername(request.getUsername());
        c.setPassword(request.getPassword());
        c.setAlamat(request.getAlamat());
        c.setPhone(request.getPhone());
        c.setToken(customer.getToken());
        customerRepository.save(c);
        return toCustomerResponse(c);
    }

    public String deleteCustomerById (Integer id){
        String pesan = "berhasil delete Customer dengan id = " + id;
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ApiExceptionNotFound(idNotFound));
        customerRepository.delete(customer);
        return pesan;
    }

    private CustomerResponse toCustomerResponse(Customer customer){
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setNama(customer.getNama());
        response.setUsername(customer.getUsername());
        response.setPassword(customer.getPassword());
        response.setPhone(customer.getPhone());
        response.setAlamat(customer.getAlamat());
        return response;
    }
}
