package com.tweet.payloads;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class JwtAuthResponse {
    private String token;
    private String username;
    private String password;
    Collection<? extends GrantedAuthority> authority;
    private boolean active;

}
