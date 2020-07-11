package ru.otus.core.service;

import ru.otus.cachehw.HwCache;
import ru.otus.core.model.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

    Optional<User> findByLogin(String login);

    Optional<List<User>> findAll();

    HwCache<Long, User> getCache();

}