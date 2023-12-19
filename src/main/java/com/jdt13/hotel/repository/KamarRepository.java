package com.jdt13.hotel.repository;

import com.jdt13.hotel.entity.Kamar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KamarRepository extends JpaRepository<Kamar, Integer> {
}
