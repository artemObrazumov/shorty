package com.artemobrazumov.shorty.ip_info.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record IpInfoDTO(String countryCode) {}
