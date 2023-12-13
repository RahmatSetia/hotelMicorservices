package com.jdt13.hotel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ReportRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date startDay;
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date endDay;
}
