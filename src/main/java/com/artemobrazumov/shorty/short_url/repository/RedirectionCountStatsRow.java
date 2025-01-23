package com.artemobrazumov.shorty.short_url.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public record RedirectionCountStatsRow(Timestamp time, Integer successful, Integer wrongPassword,
                                       Integer undefined) {}
