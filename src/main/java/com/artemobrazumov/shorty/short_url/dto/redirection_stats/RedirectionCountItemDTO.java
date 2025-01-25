package com.artemobrazumov.shorty.short_url.dto.redirection_stats;

import java.time.LocalDateTime;

public record RedirectionCountItemDTO(LocalDateTime time, Integer total, Integer undefined, Integer wrongPassword,
                                      Integer successful) {}
