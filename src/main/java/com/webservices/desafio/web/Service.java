package com.webservices.desafio.web;

import com.webservices.desafio.Entities.Person;
import com.webservices.desafio.Exceptions.PasswordsNotMatchingException;
import com.webservices.desafio.Exceptions.PersonNotFoundException;
import com.webservices.desafio.dto.PersonRequest;
import com.webservices.desafio.dto.PersonResponse;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {

    private final Repository repository;

    public Person findByUsername(String username){
        return repository.findByName(username).orElseThrow();
    }

    public void save(Person person) {
        repository.save(person);
    }

    public List<PersonResponse> getAll() {
        List<Person> list = repository.findAll();
        return list.stream().map(x -> PersonResponse.toModel(x)).collect(Collectors.toList());
    }

    public void changePassword(String password, String confirmPassword, Long id) {
        if(!password.equals(confirmPassword)){
            throw new PasswordsNotMatchingException("Passwords don`t match!");
        }
        else{
            Optional<Person> person = repository.findById(id);
            Person savePerson = person.orElseThrow(() -> new PersonNotFoundException("No matching ID"));
            savePerson.setPassword(password);
            repository.save(savePerson);
        }
    }

    public  Person findById(Long id){
       var x = repository.findById(id);
       return x.orElseThrow(() -> new PersonNotFoundException("No matching ID"));
    }
}
