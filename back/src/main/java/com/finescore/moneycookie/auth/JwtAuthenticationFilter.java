package com.finescore.moneycookie.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finescore.moneycookie.models.MemberInfo;
import com.sun.security.auth.UserPrincipal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    @Value("${jwt.expiration-time}")
    private final Integer EXPIRATION_TIME;
    @Value("${jwt.header-string}")
    private final String HEADER_STRING;
    @Value("${jwt.token-prefix}")
    private final String TOKEN_PREFIX;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        MemberInfo memberInfo = null;
        try {
            memberInfo = objectMapper.readValue(request.getInputStream(), MemberInfo.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                memberInfo.getEmail(),
                memberInfo.getPassword(),
                new ArrayList<>()
        );

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserPrincipal userPrincipal = (UserPrincipal) authResult.getPrincipal();

        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String token = Jwts.builder().setSubject(userPrincipal.getName()).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(key).compact();
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
