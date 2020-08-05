package ru.otus.messagesystem;

import ru.otus.messagesystem.message.Message;

import java.util.Optional;


public interface RequestHandler {
    Optional<Message> handle(Message msg);
}
