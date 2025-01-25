package com.artemobrazumov.shorty.short_url.dto.redirection_stats;

import com.artemobrazumov.shorty.short_url.dto.GroupingMethod;

import java.util.List;

public record RedirectionCountDTO(GroupingMethod groupingMethod, List<RedirectionCountItemDTO> items) {}
