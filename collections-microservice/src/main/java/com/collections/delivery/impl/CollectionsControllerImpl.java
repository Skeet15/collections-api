package com.collections.delivery.impl;

import com.collections.delivery.CollectionsController;
import com.collections.gateway.response.ResponseHandler;
import com.collections.gateway.services.CollectionsFilterService;
import com.collections.gateway.services.unsplash.UnsplashService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/collections")
@RequiredArgsConstructor
@Api(value = "Collections controller")
public class CollectionsControllerImpl implements CollectionsController {

  private static final Logger logger = LoggerFactory.getLogger(CollectionsControllerImpl.class);

  private final UnsplashService unsplashService;

  private final CollectionsFilterService collectionsFilterService;

  @Override
  @GetMapping("all")
  @ApiOperation(value = "Get collections by filter")
  public ResponseEntity<Object> getAllCollectionsByFilter(@RequestParam(required = false) String filter) {
    logger.info("Get collections by filter {}", filter);
    // Llega la llamada al controlador, lo primero es llamar a Unsplash para extraer las colecciones, luego
    // se realiza el filtrado y para terminar se forma la respuesta
    return ResponseHandler.generateResponse("Get collections by filter OK",
            collectionsFilterService.filter(unsplashService.getCollections(), filter),
            HttpStatus.OK);
  }

  @Override
  @GetMapping("callback")
  @ApiOperation(value = "Callback oauth token")
  public ResponseEntity<Object> accessTokenCallback(@RequestParam String code) {
    // Llamada callback para obtener el access token
    return ResponseHandler.generateResponse("Successfully user authentication",
            unsplashService.getAccessToken(code), HttpStatus.OK);
  }

}
