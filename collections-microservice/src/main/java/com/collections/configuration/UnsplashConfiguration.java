package com.collections.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@AllArgsConstructor
public class UnsplashConfiguration {

    private final Environment env;

    public String getPath() {
            return this.env.getProperty("unsplash.collections.path");
        }

    public String getHost() {
        return this.env.getProperty("unsplash.host");
    }

    public String getClientId() {
        return this.env.getProperty("unsplash.client-id");
    }

}
