package com.cargowhale.docker.security;

import com.cargowhale.docker.config.security.jwt.TokenProvider;
import com.cargowhale.docker.exception.CargoWhaleErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(final TokenProvider tokenProvider, final AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/authenticate")
    public JWTToken authenticate(@Valid @RequestBody final UserCredentials credentials) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new JWTToken(this.tokenProvider.createToken(authentication));
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<CargoWhaleErrorMessage> handleAuthenticationException(final HttpServletRequest request, final AuthenticationException ex) {
        CargoWhaleErrorMessage errorMessage = new CargoWhaleErrorMessage(request.getRequestURI(), ex.getMessage(), ex.getClass().toString());
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }
}
