package ru.otus.core.service;

import ru.otus.core.model.User;

import java.util.ArrayList;
import java.util.List;

public class DBInitServiseImpl implements DBInitServise {
    public DBInitServiseImpl() {
    }

    @Override
    public void initUserDb(DBServiceUser dbServiceUser) {
        List<User> list = new ArrayList<>();
        for (int i = 0; i <= 15; i++) {
            User user = new User();
            user.setName("Misha" + i);
            user.setLogin("alfa_" + i);
            user.setPassword(i + "");
            list.add(user);
        }
        list.stream().forEach(u -> dbServiceUser.saveUser(u));
    }
}
