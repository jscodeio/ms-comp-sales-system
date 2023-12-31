package io.jscode.microservice.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jscode.microservice.dto.LoginDTO;
import io.jscode.microservice.security.JwtUtil;

@RestController
@RequestMapping("auth")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil){
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }

    @PostMapping("login")
    public ResponseEntity<Void> login(@RequestBody LoginDTO request){
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(login);
        System.out.println(authentication.getName());
        String jwt = this.jwtUtil.create(authentication.getName());

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
    }
}
