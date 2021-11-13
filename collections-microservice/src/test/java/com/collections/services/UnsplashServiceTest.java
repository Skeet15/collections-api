package com.collections.services;

import com.collections.controller.UnsplashExampleResponses;
import com.collections.gateway.dto.CollectionsResponseDto;
import com.collections.gateway.services.CollectionConverter;
import com.collections.gateway.services.httpclient.CollectionsHttpClient;
import com.collections.gateway.services.unsplash.impl.UnsplashServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        locations = "classpath:application.properties")
@AutoConfigureMockMvc
public class UnsplashServiceTest {


  @Autowired
  private UnsplashServiceImpl unsplashServiceImpl;

  @MockBean
  private CollectionsHttpClient collectionsHttpClient;

  @Autowired
  private CollectionConverter collectionConverter;


  @Test
  public void whenCallToGetCollections_thenCallToHttpClientService_andGetEmptyResponse() {
    Mockito.doReturn("[]").when(collectionsHttpClient).get(Mockito.anyString(), Mockito.anyMap());
    CollectionsResponseDto collections = unsplashServiceImpl.getCollections();
    assertThat(collections.getData()).isEqualTo(CollectionsResponseDto.builder().data(new ArrayList<>()).build().getData());
  }

  @Test
  public void whenCallToGetCollections_thenCallToHttpClientService_andGetResponse() {
    Mockito.doReturn(UnsplashExampleResponses.GET_COLLECTIONS_TEST_CASE).when(collectionsHttpClient).get(Mockito.anyString(), Mockito.anyMap());
    CollectionsResponseDto collections = unsplashServiceImpl.getCollections();
    assertThat(collections).isEqualTo(collectionConverter.apply(UnsplashExampleResponses.GET_COLLECTIONS_TEST_CASE));
  }
}
