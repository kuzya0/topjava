package ru.javawebinar.topjava.web;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.LocalDate;
import java.time.LocalTime;

public class RestFilter {

    @DateTimeFormat(iso = ISO.DATE)
    LocalDate startDate;

    @DateTimeFormat(iso = ISO.DATE)
    LocalDate endDate;

    @DateTimeFormat(iso = ISO.TIME)
    LocalTime startTime;

    @DateTimeFormat(iso = ISO.TIME)
    LocalTime endTime;

    public RestFilter() {
    }

    public RestFilter(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
