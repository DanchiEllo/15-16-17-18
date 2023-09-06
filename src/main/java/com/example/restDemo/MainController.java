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
    //URL при подключении к бд "jdbc:h2:mem:demo-db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE"
    private PersonRepository repository;

    @Autowired
    public MainController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/persons")
    public Iterable<Person> getPersons() {
        return repository.findAll();
    }

    @GetMapping("/persons/{id}")
    public Optional<Person> findPersonById(@PathVariable int id) {
        return repository.findById(id);
    }
    @PostMapping("/persons")
    public Person addPerson(@RequestBody Person person) {
        repository.save(person);
        return person;
    }
    @PutMapping("/persons/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person person) {
        Optional<Person> existingPerson = repository.findById(id);

        if (existingPerson.isPresent()) {
            Person updatedPerson = existingPerson.get();
            // Обновление полей, но без изменения идентификатора
            updatedPerson.setFirstname(person.getFirstname());
            updatedPerson.setSurname(person.getSurname());
            updatedPerson.setLastname(person.getLastname());
            updatedPerson.setBirthday(person.getBirthday());
            // Обновлите другие поля в соответствии с вашим объектом Person

            HttpStatus status = repository.existsById(id) ? HttpStatus.OK : HttpStatus.CREATED;
            return new ResponseEntity<>(repository.save(updatedPerson), status);
        } else {
            person.setId(id);
            return new ResponseEntity<>(repository.save(person), HttpStatus.CREATED);
        }
    }
    @DeleteMapping("/persons/{id}")
    public void deletePerson(@PathVariable int id) {
        repository.deleteById(id);
    }

}
