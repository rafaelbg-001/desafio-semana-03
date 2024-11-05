package com.webservices.desafio.Auth;

import com.webservices.desafio.Entities.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserDetails extends User {

    private Person usuario;

    public UserDetails(Person usuario) {
        super(usuario.name, usuario.password, AuthorityUtils.createAuthorityList(ROLE.COMMON_USER.name()));
        this.usuario = usuario;
    }

    public Long getId(){
        return usuario.getId();
    }

    public String getName(){
        return usuario.getName();
    }
}
