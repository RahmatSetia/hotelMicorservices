package com.jdt13.hotel.repository;

import com.jdt13.hotel.entity.Pinalti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PinaltiRepository extends JpaRepository<Pinalti, Integer> {
    List<Pinalti> bookingId(Integer id);
}
