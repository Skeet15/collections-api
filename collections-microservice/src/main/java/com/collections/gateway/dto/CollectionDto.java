package com.collections.gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionDto {
  @JsonProperty("id")
  @NonNull
  private String id;

  @JsonProperty("title")
  private String title;

  @JsonProperty("description")
  private String description;

  @JsonProperty("cover_photo_id")
  private String coverPhotoId;

  @SuppressWarnings("unchecked")
  @JsonProperty("cover_photo")
  private void unpackNested(Map<String, Object> coverPhoto) {
    this.coverPhotoId = (String) coverPhoto.get("id");

  }

}
