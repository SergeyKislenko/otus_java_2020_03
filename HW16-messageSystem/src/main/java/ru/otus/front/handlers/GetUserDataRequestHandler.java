package ru.otus.front.handlers;

import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.messagesystem.MessageBuilder;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.Serializers;

import java.util.Optional;


public class GetUserDataRequestHandler implements RequestHandler {
    private final DBServiceUser dbServiceUser;

    public GetUserDataRequestHandler(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        User user = Serializers.deserialize(msg.getPayload(), User.class);
        dbServiceUser.saveUser(user);
        return Optional.of(MessageBuilder.buildReplyMessage(msg, user));
    }
}
