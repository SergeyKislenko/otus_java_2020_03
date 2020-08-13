package ru.otus.messagesystem.message;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private final UUID id = UUID.randomUUID();
    private final String from;
    private final String to;
    private final UUID sourceMessageId;
    private final String type;
    private final byte[] payload;

    public Message(String from, String to, UUID sourceMessageId, String type, byte[] payload) {
        this.from = from;
        this.to = to;
        this.sourceMessageId = sourceMessageId;
        this.type = type;
        this.payload = payload;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) &&
                Objects.equals(from, message.from) &&
                Objects.equals(to, message.to) &&
                Objects.equals(sourceMessageId, message.sourceMessageId) &&
                Objects.equals(type, message.type) &&
                Arrays.equals(payload, message.payload);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, from, to, sourceMessageId, type);
        result = 31 * result + Arrays.hashCode(payload);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", sourceMessageId=" + sourceMessageId +
                ", type='" + type + '\'' +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getType() {
        return type;
    }

    public byte[] getPayload() {
        return payload;
    }

    public Optional<UUID> getSourceMessageId() {
        return Optional.ofNullable(sourceMessageId);
    }
}
