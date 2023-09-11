package com.example.restDemo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    PersonRepository repository;

    public Iterable<Person> getAllPersons() {
        return repository.findAll();
    }

    public Optional<Person> findPersonById(Long id) {
        return repository.findById(id);
    }

    public Person addPerson(Person person) {
        return repository.save(person);
    }

    public Person addMessageToPerson(Long personId, Message message) {
        Person person = repository.findById(personId).get();
        message.setPerson(person);
        message.setTime(LocalDateTime.now());
        person.addMessage(message);
        return repository.save(person);
    }

    public Person updatePerson(Long id, Person person) {
        Optional<Person> existingPerson = repository.findById(id);

        if (existingPerson.isPresent()) {
            Person updatedPerson = existingPerson.get();
            updatedPerson.setFirstname(person.getFirstname());
            updatedPerson.setSurname(person.getSurname());
            updatedPerson.setLastname(person.getLastname());
            updatedPerson.setBirthday(person.getBirthday());

            return repository.save(updatedPerson);

        } else {
            person.setId(id);
            return repository.save(person);
        }
    }

    @Transactional
    public void deletePerson(Long id) {
        repository.deleteById(id);
    }


}
