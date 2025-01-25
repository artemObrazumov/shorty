package com.artemobrazumov.shorty.short_url.dto;

public enum GroupingMethod {
    BY_DAY("1 day", "day"),
    BY_MONTH("1 month", "month");

    private final String interval;
    private final String dateTruncUnit;

    GroupingMethod(String interval, String dateTruncUnit) {
        this.interval = interval;
        this.dateTruncUnit = dateTruncUnit;
    }

    public String getInterval() {
        return interval;
    }

    public String getDateTruncUnit() {
        return dateTruncUnit;
    }
}
