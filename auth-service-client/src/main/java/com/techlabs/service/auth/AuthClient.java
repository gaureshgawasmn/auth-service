package com.techlabs.service.auth;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthClient {

    @POST("/auth/authenticate")
    boolean authenticate(@Body AuthRequest authRequest);
}
