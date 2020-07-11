package ru.otus.server;

public interface AdminWebServer {
    void start() throws Exception;

    void join() throws Exception;

    void stop() throws Exception;
}
