package com.collections.gateway.services.httpclient;


import com.collections.gateway.dto.CollectionsResponseDTO;
import org.springframework.http.ResponseEntity;

public interface CollectionsHttpClient {
    String get(String uri);
    String post(String uri);
}
