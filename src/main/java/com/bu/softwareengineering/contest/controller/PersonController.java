package com.bu.softwareengineering.contest.controller;

import com.bu.softwareengineering.contest.domain.Person;
import com.bu.softwareengineering.contest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@Transactional
public class PersonController {
    @Autowired
    PersonRepository personRepository;

    @PostMapping("/person")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {

        Person result = personRepository.save(person);
        return new ResponseEntity(result, HttpStatus.CREATED);
    }


}
