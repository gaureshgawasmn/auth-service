package com.techlabs.service.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
