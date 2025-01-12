package com.techlabs.service.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthUser {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
}
