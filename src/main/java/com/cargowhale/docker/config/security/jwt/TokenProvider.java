package com.cargowhale.docker.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    public static final String AUTHORITIES_KEY = "auth";

    private static final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private final JwtProperties properties;

    @Autowired
    public TokenProvider(final JwtProperties properties) {
        this.properties = properties;
    }

    public String createToken(final Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + validityInMS());

        return Jwts.builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(SignatureAlgorithm.HS512, this.properties.getSecret())
            .setExpiration(validity)
            .compact();
    }

    Authentication getAuthentication(final String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(this.properties.getSecret())
            .parseClaimsJws(token)
            .getBody();

        List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    boolean validateToken(final String authToken) {
        try {
            Jwts.parser().setSigningKey(this.properties.getSecret()).parseClaimsJws(authToken);
            return true;
        } catch (final SignatureException e) {
            log.info("Invalid JWT signature: " + e.getMessage());
            return false;
        }
    }

    private long validityInMS() {
        return 1000 * this.properties.getTokenValidityInSeconds();
    }
}
