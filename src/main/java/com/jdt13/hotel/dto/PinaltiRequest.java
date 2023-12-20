package com.jdt13.hotel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class PinaltiRequest {
    @NotBlank
    private Integer bookingId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date checkout;
}
