package com.nico.auth.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    // Va a ser la respuesta independientemente si es el registro o es el login,
    // nos interesa que nos devuelva el token

    String token;

}
