package com.webservices.desafio.web.Security;

import com.webservices.desafio.Auth.JWT.JwtToken;
import com.webservices.desafio.Auth.JwtUserDetailsService;
import com.webservices.desafio.dto.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
@Slf4j
public class SecurityController {

    private final JwtUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "api/v1/login")
    public ResponseEntity <String> authenticate(@RequestBody LoginRequest login, HttpServletRequest request){

        try{

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
            authenticationManager.authenticate(authenticationToken);
            JwtToken token = userDetailsService.getTokenAuthenticated(login.getUsername());
            return ResponseEntity.ok().body("Token: \n" + token.getToken());
        } catch (AuthenticationException e) {
            log.warn("Bad Credentials for " + login.getUsername());
        }
        return ResponseEntity.badRequest().build();
    }
}
