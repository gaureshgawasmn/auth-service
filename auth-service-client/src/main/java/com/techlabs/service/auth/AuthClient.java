package com.techlabs.service.auth;

import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthClient {

    @POST("/auth/authenticate")
    AuthUser authenticate(@Body AuthRequest authRequest);
}
