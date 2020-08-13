package ru.otus.front;

import ru.otus.core.model.User;
import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagesystem.message.MessageId;

import java.util.Optional;
import java.util.function.Consumer;

public interface FrontendService {

    void saveUser(User user, MessageCallback<User> dataConsumer);

    <T> Optional<Consumer<T>> takeConsumer(MessageId sourceMessageId, Class<T> tClass);

}

