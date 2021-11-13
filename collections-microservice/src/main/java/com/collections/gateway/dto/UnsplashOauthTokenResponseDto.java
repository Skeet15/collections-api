package com.collections.gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnsplashOauthTokenResponseDto extends ResponseDto {
  @JsonProperty("access_token")
  @NonNull
  private String accessToken;
  @JsonProperty("token_type")
  @NonNull
  private String tokenType;
  @JsonProperty("refresh_token")
  @NonNull
  private String refreshToken;
  @JsonProperty("scope")
  @NonNull
  private String scope;
  @JsonProperty("created_at")
  @NonNull
  private long createdAt;
}
