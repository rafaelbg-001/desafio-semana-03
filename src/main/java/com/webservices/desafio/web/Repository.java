package com.webservices.desafio.web;

import com.webservices.desafio.Entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository <Person, Long> {
}
