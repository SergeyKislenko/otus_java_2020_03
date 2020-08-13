package ru.otus.messagesystem.message;

public enum MessageType {
    CREATE_USER("CreateUser");

    private final String name;

    MessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
