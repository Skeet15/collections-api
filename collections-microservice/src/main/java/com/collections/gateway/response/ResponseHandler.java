package com.collections.gateway.response;

import com.collections.gateway.dto.ResponseDto;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



public class ResponseHandler {

  private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

  public static ResponseEntity<Object> generateResponse(String message, ResponseDto responseDto, HttpStatus status) {
    // Metodo genérico para montar la respuesta al cliente, cualquier DTO debe extener de la ResponseDTO
    responseDto.setMessage(message);
    responseDto.setTimestamp(Instant.now().getEpochSecond());
    responseDto.setStatusCode(HttpStatus.OK.toString());
    logger.info("Generating response: message {} status {} ", message, status);
    logger.info("Building response: {}", responseDto);
    return new ResponseEntity<>(responseDto, status);
  }


  public static ResponseEntity<Object> generateResponse(String message, HttpStatus status) {
    // Método sobrecargado para prescindir de ResponseDTO
    Map<String, Object> map = new HashMap<>();
    map.put("message", message);
    map.put("status", status);
    map.put("statusCode", status.value());
    map.put("timestamp", Instant.now().getEpochSecond());
    logger.info("Generating response: message {} status {} ", message, status);
    logger.info("Building response: {}", map);
    return new ResponseEntity<>(map, status);
  }
}
