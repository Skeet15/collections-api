package com.collections.services;

import com.collections.configuration.UnsplashConfiguration;
import com.collections.controller.UnsplashExampleResponses;
import com.collections.gateway.services.httpclient.CollectionsHttpClient;
import com.collections.gateway.services.unsplash.impl.UnsplashServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        locations = "classpath:application.properties")
public class UnsplashServiceTest {


    @Autowired
    private UnsplashServiceImpl unsplashServiceImpl;

    @MockBean
    private CollectionsHttpClient collectionsHttpClient;

    @MockBean
    private UnsplashConfiguration unsplashConfiguration;


    @Test
    public void whenCallToGetCollections_thenCallToHttpClientService_andGetEmptyResponse() throws Exception {
        Mockito.doReturn("[]").when(collectionsHttpClient).get(Mockito.anyString());
        String collections = unsplashServiceImpl.getCollections();
        assertThat(collections).isEqualTo("[]");
    }

    @Test
    public void whenCallToGetCollections_thenCallToHttpClientService_andGetResponse() throws Exception {
        Mockito.doReturn(UnsplashExampleResponses.GET_COLLECTIONS_TEST_CASE).when(collectionsHttpClient).get(Mockito.anyString());
        String collections = unsplashServiceImpl.getCollections();
        assertThat(collections).isEqualTo(UnsplashExampleResponses.GET_COLLECTIONS_TEST_CASE);
    }
}
