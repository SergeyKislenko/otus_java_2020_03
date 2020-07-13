package ru.otus.core.service;

import ru.otus.core.model.User;

import java.util.ArrayList;
import java.util.List;

public class DBInitServiseImpl implements DBInitServise {
    @Override
    public void initUserDb(DBServiceUser dbServiceUser) {
        List<User> list = new ArrayList<>();
        for (int i = 0; i <= 30; i++) {
            User user = new User();
            user.setName("Misha" + i);
            user.setLogin("alfa_" + i);
            user.setPassword(i + "");
            list.add(user);
        }
        User admin = new User();
        admin.setLogin("admin");
        admin.setName("Семён Семенович");
        admin.setPassword("admin");
        list.add(admin);
        list.stream().forEach(u -> dbServiceUser.saveUser(u));
    }
}
