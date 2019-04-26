package com.artirex.sutakip.RestApi;

public class BaseManager {

    protected RestApi getRestApi(){
        RestApiClient restApiClient = new RestApiClient(BaseURL.URL);
        return restApiClient.getRestApi();
    }
}
