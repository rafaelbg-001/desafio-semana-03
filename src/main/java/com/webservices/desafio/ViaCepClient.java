package com.webservices.desafio;

import com.webservices.desafio.Entities.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepClient {

    @GetMapping(value = "{cep}/json/")
    public Address getAddressByCep(@PathVariable String cep);
}
