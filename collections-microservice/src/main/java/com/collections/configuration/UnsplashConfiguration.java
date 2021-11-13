package com.collections.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@AllArgsConstructor
public class UnsplashConfiguration {

  //Parámetros relativos a la autenticacion y llamadas a Unsplash

  private final Environment env;

  public void setAccessToken(String accessToken) {
    System.setProperty("unsplash.access-token", accessToken);
  }

  public String getAccessToken() {
    return System.getProperty("unsplash.access-token");
  }


  public String getPath() {
    return this.env.getProperty("unsplash.collections.path");
  }

  public String getHost() {
    return this.env.getProperty("unsplash.host");
  }

  public String getOauthAccessTokenUri() {
    return this.env.getProperty("spring.security.oauth2.client.provider.unsplash.token-uri");
  }

  public String getClientId() {
    return this.env.getProperty("spring.security.oauth2.client.registration.unsplash.client-id");
  }

  public String getOauthClientSecret() {
    return this.env.getProperty("spring.security.oauth2.client.registration.unsplash.client-secret");
  }

  public String getOauthRedirectUri() {
    return this.env.getProperty("spring.security.oauth2.client.registration.unsplash.redirect-uri");
  }

  public String getOauthAuthorizationGrantType() {
    return this.env.getProperty("spring.security.oauth2.client.registration.unsplash.authorization-grant-type");
  }

}
