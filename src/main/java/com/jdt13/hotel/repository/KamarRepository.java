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
    @Query("SELECT k " +
            "FROM Booking b " +
            "RIGHT JOIN b.kamar k " +
            "WHERE b.statusBooking IS NULL " +
            "AND b.checkin > :checkin " +
            "AND b.checkout <    :checkout")

    List<Kamar> findKamarBeforeBookingInCheckinCheckout(@Param("checkin") Date checkin, @Param("checkout") Date checkout);
}
