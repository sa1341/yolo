package com.junyoung.yolo.domain;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
public class LocalDateParser {
    private LocalDate searchDate;

    public LocalDateParser(String currentDate) {
        this.searchDate = LocalDate.parse(currentDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public LocalDateTime startDate() {
        return this.searchDate.atStartOfDay();
    }

    public LocalDateTime endDate() {
        return LocalDateTime.of(searchDate, LocalTime.of(23, 59, 59));
    }
}
