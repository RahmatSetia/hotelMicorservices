package com.jdt13.hotel.repository;

import com.jdt13.hotel.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository <Booking, Integer> {
    List<Booking> statusBookingFalse();
    List<Booking> statusBookingTrue();
    List<Booking> statusBookingNull();
    @Query("Select b From Booking b " +
            "where b.checkin >= :dayStart " +
            "And b.checkout <= :dayEnd")
    List<Booking> reportByDate(@Param("dayStart") Date dayStart, @Param("dayEnd") Date dayEnd);
}