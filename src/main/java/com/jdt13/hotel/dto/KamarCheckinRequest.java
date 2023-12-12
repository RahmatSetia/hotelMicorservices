package com.jdt13.hotel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class KamarCheckinRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date checkin;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date checkout;
}
