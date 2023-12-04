package com.jdt13.hotel.repository;

import com.jdt13.hotel.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository <Customer, Integer> {
    Optional<Customer> findByUsernameAndPassword(String username, String password);
    Optional<Customer> findByToken(String token);
}
