package com.collections.gateway.services.httpclient;

import java.util.Map;

public interface CollectionsHttpClient {
  String get(String uri, Map<String, String> headers);

  String post(String uri, Map<String, String> headers);
}
