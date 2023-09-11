package com.example.restDemo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


// Вся логика в сервисе (4.18-16)
@Service
public class PersonService {
    @Autowired
    PersonRepository repository;

    public Iterable<Person> getAllPersons() {
        return repository.findAll();
    }

    public Iterable<Message> getAllMessagesToPerson(Long id) {
        if (repository.existsById(id)) {
            return repository.findById(id).get().getMessages();
        }
        else {
            return null;
        }
    }

    public Optional<Person> findPersonById(Long id) {
        return repository.findById(id);
    }

    // Дополнил метод, если есть сообщения при создании
    //пользователя и у них не определено person и time,
    //автоматически их добавляет как и в в методе addMessageToPerson
    public Person addPerson(Person person) {
        if (person.messages != null) {
            for (Message m : person.messages) {
                m.setPerson(person);
                m.setTime(LocalDateTime.now());
            }
        }
        var a = person.getMessages();
        return repository.save(person);
    }

    // Если как-то можно решить повторения return null; напишите пожалуйста
    public Person deleteMessageToPerson(Long personId, Long messageId) {
        if (repository.existsById(personId)) {
            Person person = repository.findById(personId).get();
            List<Message> messages = person.getMessages();

            Optional<Message> messageToRemove = messages.stream()
                    .filter(message -> message.getId().equals(messageId))
                    .findFirst();

            if (messageToRemove.isPresent()) {
                messages.remove(messageToRemove.get());

                repository.save(person);

                return person;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Person addMessageToPerson(Long personId, Message message) {
        if (repository.existsById(personId)) {
            Person person = repository.findById(personId).get();
            message.setPerson(person);
            message.setTime(LocalDateTime.now());
            person.addMessage(message);
            return repository.save(person);
        }
        else {
            return null;
        }
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
