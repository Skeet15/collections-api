package com.collections.gateway.services;

import com.collections.gateway.dto.UnsplashOAuthTokenResponseDto;
import com.collections.shared.converter.GenericConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenConverter implements GenericConverter<String, UnsplashOAuthTokenResponseDto> {

    ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenConverter.class);

    @Override
    public UnsplashOAuthTokenResponseDto apply(String body) {
        UnsplashOAuthTokenResponseDto unsplashOAuthTokenResponseDto = null;
        try {
            logger.info("Mapping POST token to UnsplashOAuthTokenResponseDto");
            unsplashOAuthTokenResponseDto = mapper.readValue(body, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            throw new IllegalStateException();
        }
        return unsplashOAuthTokenResponseDto;
    }
}
