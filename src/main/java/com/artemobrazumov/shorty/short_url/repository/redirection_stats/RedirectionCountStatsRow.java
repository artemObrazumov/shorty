package com.artemobrazumov.shorty.short_url.repository.redirection_stats;

import java.sql.Timestamp;

public record RedirectionCountStatsRow(Timestamp time, Integer total, Integer successful, Integer wrongPassword,
                                       Integer undefined) {}
