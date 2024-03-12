package com.nico.jwt.service;

import com.nico.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    public String getToken(UserDetails user) {
        return null;
    }
}
