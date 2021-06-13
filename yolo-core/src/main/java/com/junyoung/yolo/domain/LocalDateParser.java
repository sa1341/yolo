package com.junyoung.yolo.domain;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
public class LocalDateParser {

    public LocalDateParser() {
    }

    public static LocalDateTime parseStartDate(String startDate) {
        LocalDate localStartDate = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return localStartDate.atStartOfDay();
    }

    public static LocalDateTime parseEndDate(String endDate) {
        LocalDate localEndDate = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return LocalDateTime.of(localEndDate, LocalTime.of(23, 59, 59));
    }
}
