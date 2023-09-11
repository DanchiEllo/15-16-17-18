package com.example.restDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class MainController {
    private PersonService personService;
    private MessageService messageService;

    //URL при подключении к бд "jdbc:h2:mem:demo-db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"

    @Autowired
    public MainController(PersonService personService, MessageService messageService) {
        this.personService = personService;
        this.messageService = messageService;

    }

    // GET requests
    @GetMapping("/persons")
    public ResponseEntity<Iterable<Person>> getAllPersons() {
        return new ResponseEntity<>(personService.getAllPersons(), HttpStatus.OK);
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<Optional<Person>> findPersonById(@PathVariable Long id) {
        return new ResponseEntity<>(personService.findPersonById(id), HttpStatus.OK);
    }

    @GetMapping("/messages")
    public ResponseEntity<Iterable<Message>> getAllMessages() {
        return new ResponseEntity<>(messageService.getAllMessages(), HttpStatus.OK);
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<Optional<Message>> findMessageById(@PathVariable Long id) {
        return new ResponseEntity<>(messageService.findMessageById(id), HttpStatus.OK);
    }


    // POST requests
    @PostMapping("/persons")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        return new ResponseEntity<>(personService.addPerson(person), HttpStatus.CREATED);
    }

    @PostMapping("/persons/{id}/messages")
    public Person addMessageToPerson(@PathVariable Long id, @RequestBody Message message) {
        return personService.addMessageToPerson(id, message);
    }

    // PUT requests
    @PutMapping("/persons/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        return new ResponseEntity<>(personService.updatePerson(id, person), HttpStatus.OK);
    }

    @PutMapping("/messages/{id}")
    public ResponseEntity<Message> updateMessageById(@PathVariable Long id, @RequestBody Message message) {
        return new ResponseEntity<>(messageService.updateMessageById(id, message), HttpStatus.OK);
    }



    // DELETE requests
    @DeleteMapping("/persons/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Message> deleteMessageById(@PathVariable Long id) {
        messageService.deleteMessageById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
