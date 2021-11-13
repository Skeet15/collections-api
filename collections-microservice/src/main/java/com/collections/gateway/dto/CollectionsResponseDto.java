package com.collections.gateway.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class CollectionsResponseDto extends ResponseDto {
  @NonNull
  List<CollectionDto> data;
}
