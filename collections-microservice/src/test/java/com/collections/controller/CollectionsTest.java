package com.collections.controller;

import com.collections.gateway.dto.CollectionsResponseDto;
import com.collections.gateway.services.CollectionConverter;
import com.collections.gateway.services.unsplash.impl.UnsplashServiceImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static com.collections.controller.UnsplashExampleResponses.GET_COLLECTIONS_TEST_CASE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties")
public class CollectionsTest {


  @MockBean
  private UnsplashServiceImpl unsplashServiceImpl;

  @Autowired
  private CollectionConverter collectionConverter;

  @Autowired
  private MockMvc mvc;

  private static JSONObject getFirstItemDataFromResponse(MvcResult result) throws UnsupportedEncodingException, JSONException {
    return new JSONObject(new JSONArray(new JSONObject(result.getResponse().getContentAsString()).get("data").toString()).get(0).toString());
  }

  @Test
  public void whenGetEmptyResponse_thenStatus200WithEmptyResponse_collectionsAll() throws Exception {
    Mockito.doReturn(CollectionsResponseDto.builder()
            .data(new ArrayList<>())
            .build()).when(unsplashServiceImpl).getCollections();
    MvcResult result = mvc.perform(get("/collection/all?filter=1")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andReturn();
    JSONAssert.assertEquals(
            new JSONArray(new JSONObject(result.getResponse().getContentAsString()).get("data").toString()), new JSONArray("[]"),
            JSONCompareMode.STRICT);
  }

  @Test
  public void whenGetCollections_thenStatus200WithResponse_collectionsAll() throws Exception {
    Mockito.doReturn(collectionConverter.apply(GET_COLLECTIONS_TEST_CASE)).when(unsplashServiceImpl).getCollections();
    MvcResult result = mvc.perform(get("/collection/all?filter=1")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()).andReturn();
    JSONAssert.assertEquals(
            new JSONObject("{\"id\":\"8961198\",\"title\":\"Patterns\",\"description\":null,\"cover_photo_id\":\"VfhCCVr5tgg\"}"),
            getFirstItemDataFromResponse(result), JSONCompareMode.STRICT);
  }

}
