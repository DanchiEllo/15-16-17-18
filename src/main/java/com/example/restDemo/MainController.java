package com.example.restDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class MainController {
    private PersonService personService;
    //URL при подключении к бд "jdbc:h2:mem:demo-db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"

    @Autowired
    public MainController(PersonService personService) {
        this.personService = personService;
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

    // Получить все сообщения конкретного пользователя по его id (4.18-16)
    @GetMapping("/persons/{id}/messages")
    public ResponseEntity<Iterable<Message>> getAllMessagesToPerson(@PathVariable Long id) {
        if (personService.getAllMessagesToPerson(id) != null) {
            return new ResponseEntity<>(personService.getAllMessagesToPerson(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    // POST requests
    @PostMapping("/persons")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        return new ResponseEntity<>(personService.addPerson(person), HttpStatus.CREATED);
    }

    @PostMapping("/persons/{id}/messages")
    public ResponseEntity<Person> addMessageToPerson(@PathVariable Long id, @RequestBody Message message) {
        Person person = personService.addMessageToPerson(id, message);
        if (person != null) {
            return new ResponseEntity<>(person, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    // PUT requests
    @PutMapping("/persons/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person person) {
        return new ResponseEntity<>(personService.updatePerson(id, person), HttpStatus.OK);
    }



    // DELETE requests
    @DeleteMapping("/persons/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Удалить сообщение конкретного пользователя по его id (4.18-16)
    @DeleteMapping("/persons/{personId}/messages/{messageId}")
    public ResponseEntity<Person> deleteMessageToPerson(@PathVariable Long personId, @PathVariable Long messageId) {
        Person person = personService.deleteMessageToPerson(personId, messageId);
        if (person != null) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
