package com.jdt13.hotel.repository;

import com.jdt13.hotel.entity.Receptionist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceptionistRepository extends JpaRepository<Receptionist, Integer> {
    Optional<Receptionist> findByUsernameAndPassword(String username, String password);
    Optional<Receptionist> findByToken(String token);
}
