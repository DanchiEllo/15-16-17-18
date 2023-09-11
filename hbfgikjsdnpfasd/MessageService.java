package com.example.restDemo;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    MessageRepository repository;

    public Iterable<Message> getAllMessages() {
        return repository.findAll();
    }

    public Optional<Message> findMessageById(Long id) {
        return repository.findById(id);
    }

    public Message updateMessageById(Long id, Message message) {
        Optional<Message> existingMessage = repository.findById(id);

        if (existingMessage.isPresent()) {
            Message updatedMessage = existingMessage.get();
            updatedMessage.setTitle(message.getTitle());
            updatedMessage.setText(message.getText());
            updatedMessage.setTime(message.getTime());
            updatedMessage.setPerson(message.getPerson());

            return repository.save(updatedMessage);

        } else {
            message.setId(id);
            return repository.save(message);
        }
    }


    public void deleteMessageById(Long messageId) {
        Optional<Message> optionalMessage = repository.findById(messageId);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            Person person = message.getPerson();
            person.getMessages().remove(message); // Удаляем сообщение из списка владельца
            repository.deleteById(messageId); // Удаляем сообщение из базы данных
        }
    }

}
