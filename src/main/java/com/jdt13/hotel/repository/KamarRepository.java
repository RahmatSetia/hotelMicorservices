package com.jdt13.hotel.repository;

import com.jdt13.hotel.entity.Kamar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface KamarRepository extends JpaRepository<Kamar, Integer> {
    @Query(value = "SELECT k " +
            "FROM Booking b " +
            "RIGHT JOIN b.kamar k " +
            "WHERE b.statusBooking IS Empty")
    List<Kamar> findKamarBeforeBookingInCheckinCheckout();

    @Query(value = "SELECT k " +
            "FROM Booking b " +
            "RIGHT JOIN b.kamar k " +
            "WHERE b.statusBooking IS Empty")
    List<Object> findKamarBeforeBookingInCheckinCheckout(@Param("checkin") Date checkin, @Param("checkout") Date checkout);
}
