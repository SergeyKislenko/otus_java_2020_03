package ru.otus.messagesystem;

import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.messagesystem.message.Serializers;


public class MessageBuilder {
    static final Message VOID_MESSAGE =
            new Message(null, null,
                    null, "voidTechnicalMessage", new byte[1]);

    private MessageBuilder() {
    }

    public static Message getVoidMessage() {
        return VOID_MESSAGE;
    }


    public static <T extends Object> Message buildReplyMessage(Message message, T data) {
        return new Message(message.getTo(), message.getFrom(), message.getId(), MessageType.CREATE_USER.getName(), Serializers.serialize(data));
    }

}
