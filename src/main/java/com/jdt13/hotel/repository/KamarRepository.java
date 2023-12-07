package com.jdt13.hotel.repository;

import com.jdt13.hotel.entity.Kamar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface KamarRepository extends JpaRepository<Kamar, Integer> {
    Optional<Kamar> findByNoKamar(Integer noKamar);
    @Query("SELECT k.id, k.noKamar, k.harga " +
            "FROM Kamar k " +
            "LEFT JOIN Booking b ON k.id = b.kamar.id " +
            "AND :checkin > b.checkin " +
            "AND :checkout < b.checkout " +
            "WHERE b.statusBooking IS NULL")
    List<Kamar> findKamarBeforeBookingInCheckinCheckout(@Param("checkin") Date checkin, @Param("checkout") Date checkout);
}
