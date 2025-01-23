package com.artemobrazumov.shorty.short_url.dto;

import java.time.LocalDateTime;

public record RedirectionsCountItemDTO(LocalDateTime time, Integer undefined, Integer wrongPassword,
                                       Integer successful) {}
