package com.collections.gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionDTO {
    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("cover_photo_id")
    private String coverPhotoId;

    @SuppressWarnings("unchecked")
    @JsonProperty("cover_photo")
    private void unpackNested(Map<String,Object> coverPhoto) {
        this.coverPhotoId = (String)coverPhoto.get("id");
    }



}
