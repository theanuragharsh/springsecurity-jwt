package com.springsecurity.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Integer EXPIRATION_DAYS = 16;
    private static final String SECRET = "ThisIsTheSecretKeyForJwtTokenAndMustBeKeptPrivate";

    private final AuthenticationManager authenticationManager;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {

            UsernameAndPasswordAuthenticationRequest usernameAndPasswordAuthenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(usernameAndPasswordAuthenticationRequest.getUsername(),
                    usernameAndPasswordAuthenticationRequest.getPassword());
            return authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        } catch (IOException ioException) {
            throw new BadCredentialsException("Invalid Request" + ioException.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = generateToken(authResult, SECRET);
        response.addHeader("Authorization", "Bearer " + token);
    }

    //    This method will generate new token if authentication is successful
    private String generateToken(Authentication authResult, String secret) {
        return Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(calculateExpirationDate("issuedAt"))
                .setExpiration(calculateExpirationDate("expiresAt"))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    //    This method is used to set the issue and expiration time for the token
    private Date calculateExpirationDate(String dateType) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (dateType.equals("expiresAt")) {
            calendar.add(Calendar.DAY_OF_MONTH, EXPIRATION_DAYS);
        }
        return calendar.getTime();
    }
}
