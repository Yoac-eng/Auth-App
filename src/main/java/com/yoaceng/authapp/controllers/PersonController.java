package com.yoaceng.authapp.controllers;

import com.yoaceng.authapp.domain.person.Person;
import com.yoaceng.authapp.domain.person.PersonDTO;
import com.yoaceng.authapp.domain.person.PersonResponseDTO;
import com.yoaceng.authapp.repositories.PersonRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Default controller to handle Person related requests.
 *
 * @author Cayo Cutrim
 */
@RestController
@RequestMapping("/person")
public class PersonController   {

    @Autowired
    PersonRepository repository;

    @PostMapping
    public ResponseEntity postPerson(@RequestBody @Valid PersonDTO body){
        Person newPerson = new Person(body);

        this.repository.save(newPerson);
        return ResponseEntity.ok().build();
    }


    @GetMapping
    public ResponseEntity getAllPerson(){
        List<PersonResponseDTO> personList = this.repository.findAll().stream().map(PersonResponseDTO::new).toList();

        return ResponseEntity.ok(personList);
    }

}
