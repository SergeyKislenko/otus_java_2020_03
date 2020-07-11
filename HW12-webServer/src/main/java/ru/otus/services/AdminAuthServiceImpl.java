package ru.otus.services;

import ru.otus.core.service.DBServiceUser;

public class AdminAuthServiceImpl implements AdminAuthService {

    private final DBServiceUser dbServiceUser;

    public AdminAuthServiceImpl(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public boolean authenticate(String name, String password) {
        return dbServiceUser.findByLogin(name)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

}

