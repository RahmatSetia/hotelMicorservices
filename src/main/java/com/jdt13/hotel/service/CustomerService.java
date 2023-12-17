package com.jdt13.hotel.service;

import com.jdt13.hotel.dto.*;
import com.jdt13.hotel.entity.Customer;
import com.jdt13.hotel.exception.ApiRequestException;
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
        return toPaymentResponse(customer);
    }

    public LoginResponse login (LoginRequest request){
        Optional<Customer> customer = customerRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        Customer c = new Customer();
        if (!customer.isPresent()){throw new ApiRequestException(idNotFound);}
        c.setId(customer.get().getId());
        c.setNama(customer.get().getNama());
        c.setUsername(customer.get().getUsername());
        c.setPassword(customer.get().getPassword());
        c.setAlamat(customer.get().getAlamat());
        c.setPhone(customer.get().getPhone());
        c.setToken(Jwt.getToken(request));
        customerRepository.save(c);

        LoginResponse response = new LoginResponse();
        response.setId(c.getId());
        response.setToken(c.getToken());
        return response;
    }

    public CustomerResponse findCustomerById (Integer id, String token){
        if (!tokenService.getToken(token)){throw new ApiRequestException(tokenNotFound);}
        Optional<Customer> cu = customerRepository.findById(id);
        if (cu.isEmpty()){
            throw new ApiRequestException(idNotFound);
        }
        return toPaymentResponse(cu.get());
    }

    public CustomerResponse updateCustomer (String token, Integer id, CustomerRequest request){
        if (!tokenService.getToken(token)){throw new ApiRequestException(tokenNotFound);}
        Optional<Customer> cu = customerRepository.findById(id);
        if (cu.isEmpty()){
            throw new ApiRequestException(idNotFound);
        }
        Customer c = new Customer();
        c.setId(cu.get().getId());
        c.setNama(request.getNama());
        c.setUsername(request.getUsername());
        c.setPassword(request.getPassword());
        c.setAlamat(request.getAlamat());
        c.setPhone(request.getPhone());
        c.setToken(cu.get().getToken());
        customerRepository.save(c);
        return toPaymentResponse(c);
    }

    public void deleteCustomerById (Integer id){
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()){
            throw new ApiRequestException(idNotFound);
        }
        customerRepository.deleteById(id);
    }

    private CustomerResponse toPaymentResponse (Customer customer){
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
