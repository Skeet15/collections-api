package com.collections.gateway.response;

import com.collections.delivery.impl.CollectionsControllerImpl;
import com.collections.gateway.dto.CollectionsResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

    public static ResponseEntity<Object> generateResponse(String message, CollectionsResponseDTO data, HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status);
        map.put("statusCode", status.value());
        map.put("timestamp", Instant.now().getEpochSecond());
        map.put("data", data.getCollections());
        logger.info("Generating response: message {} status {} ", message, status);
        logger.info("Building response: {}", map);
        return new ResponseEntity<>(map, status);
    }


    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status);
        map.put("statusCode", status.value());
        map.put("timestamp", Instant.now().getEpochSecond());
        logger.info("Generating response: message {} status {} ", message, status);
        logger.info("Building response: {}", map);
        return new ResponseEntity<>(map, status);
    }
}
