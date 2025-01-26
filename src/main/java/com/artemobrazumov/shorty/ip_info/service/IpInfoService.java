package com.artemobrazumov.shorty.ip_info.service;

import com.artemobrazumov.shorty.ip_info.dto.IpInfoDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class IpInfoService {

    private final RestClient restClient;

    public IpInfoService(RestClient restClient) {
        this.restClient = restClient;
    }

    public IpInfoDTO getIpInfo(String ip) {
        var response = restClient
                .get()
                .uri("/"+ip)
                .retrieve()
                .toEntity(IpInfoDTO.class);
        return response.getBody();
    }
}
