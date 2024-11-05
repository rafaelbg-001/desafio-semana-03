package com.webservices.desafio.Auth;

import com.webservices.desafio.Auth.JWT.JwtToken;
import com.webservices.desafio.Auth.JWT.JwtUtil;
import com.webservices.desafio.Entities.Person;
import com.webservices.desafio.web.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class JwtUserDetailsService implements UserDetailsService {


    private final Service service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person user = service.findByUsername(username);
        return new com.webservices.desafio.Auth.UserDetails(user);
    }

    public JwtToken getTokenAuthenticated(String username){
        return JwtUtil.createToken(username, ROLE.COMMON_USER.name());
    }
}
