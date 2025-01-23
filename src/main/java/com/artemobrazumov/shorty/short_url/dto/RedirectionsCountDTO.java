package com.artemobrazumov.shorty.short_url.dto;

import java.util.List;

public record RedirectionsCountDTO(GroupingMethod groupingMethod, List<RedirectionsCountItemDTO> items) {}
