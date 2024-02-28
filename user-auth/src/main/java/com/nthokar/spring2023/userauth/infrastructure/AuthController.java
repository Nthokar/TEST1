package com.nthokar.spring2023.userauth.infrastructure;

import com.nthokar.spring2023.userauth.app.UserDetails;
import com.nthokar.spring2023.userauth.app.entities.Role;
import com.nthokar.spring2023.userauth.app.entities.User;
import com.nthokar.spring2023.userauth.app.services.MyUserDetailsService;
import com.nthokar.spring2023.userauth.app.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private MyUserDetailsService userDetailsService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.email, request.password);
        Authentication auth = authManager.authenticate(authenticationToken);

        UserDetails user = (UserDetails) userDetailsService.loadUserByUsername(request.email);
        String access_token = tokenService.generateAccessToken(user);
        String refresh_token = tokenService.generateRefreshToken(user);

        return new LoginResponse("User with email = "+ request.email + " successfully logined!",
                access_token, refresh_token);
    }

    @GetMapping("/token/refresh")
    public RefreshTokenResponse refreshToken(HttpHeaders request) {
        String headerAuth = request.getFirst("Authorization");
        String refreshToken = headerAuth.substring(7, headerAuth.length());

        String email = tokenService.parseToken(refreshToken).getSubject();
        UserDetails user = (UserDetails) userDetailsService.loadUserByUsername(email);
        String access_token = tokenService.generateAccessToken(user);
        String refresh_token = tokenService.generateRefreshToken(user);

        return new RefreshTokenResponse(access_token, refresh_token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginRequest request) {
        try {
            userDetailsService.register(new User(request.email, request.firstName, request.lastName, request.password, request.role));
            return ResponseEntity.ok().body("");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @PostMapping("/validate")
    public String validate(@RequestParam String token){
        return tokenService.parseToken(token).toString();
    }
    record LoginRequest(String email, String firstName, String lastName, String password, Role role) {}
    record LoginResponse(String message, String access_jwt_token, String refresh_jwt_token) {}
    record RefreshTokenResponse(String access_jwt_token, String refresh_jwt_token) {}

}