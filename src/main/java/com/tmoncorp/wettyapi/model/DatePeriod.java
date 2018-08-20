package com.tmoncorp.wettyapi.model;

import com.tmoncorp.core.model.BaseModelSupport;

import java.time.LocalDateTime;

public class DatePeriod extends BaseModelSupport {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public DatePeriod() {

    }

    public DatePeriod(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
