package com.jdt13.hotel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PinaltiRequest {
    private Integer bookingId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date checkout;
}
