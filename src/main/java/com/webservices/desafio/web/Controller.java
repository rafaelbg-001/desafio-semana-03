package com.webservices.desafio.web;


import com.webservices.desafio.Entities.Person;
import com.webservices.desafio.ViaCepClient;
import com.webservices.desafio.dto.ChangePasswordRequest;
import com.webservices.desafio.dto.PersonRequest;
import com.webservices.desafio.dto.PersonResponse;
import com.webservices.desafio.queues.NotifylogSubscriber;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/v1")
public class Controller {

    private final Service service;
    private final ViaCepClient viacep;
    private final NotifylogSubscriber notify;

    @PostMapping
    public ResponseEntity<String> registrarPessoa(@RequestBody PersonRequest person){
        Person pessoa = PersonRequest.toModel(person);
        pessoa.setAddress(viacep.getAddressByCep(person.cep));
        service.save(pessoa);
        String s = pessoa.fromJson(pessoa);
        notify.sendMessage("\nUser Registered: \n" + s);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PutMapping(params = "id")
    public ResponseEntity changePassword(@RequestParam Long id, @RequestBody ChangePasswordRequest password){
    service.changePassword(password.password, password.confirmPassword, id);
    notify.sendMessage("\nPassword changed from user: " + service.findById(id).name);
    return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PersonResponse>> getAll(){
        List<PersonResponse> list = service.getAll();
        return ResponseEntity.ok(list);
    }
}
