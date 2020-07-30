package ru.otus.core.service;

import org.springframework.stereotype.Component;
import ru.otus.cachehw.HwCache;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class DBInitServiseImpl implements DBInitServise {
    private final DBServiceUser userService;

    public DBInitServiseImpl(UserDao userDao, HwCache<String, User> cache) {
        this.userService = new DbServiceUserImpl(userDao, cache);
    }

    @Override
    public void initUserDb() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i <= 15; i++) {
            User user = new User();
            user.setName("Misha" + i);
            user.setLogin("alfa_" + i);
            user.setPassword(i + "");
            list.add(user);
        }
        list.stream().forEach(u -> userService.saveUser(u));
    }
}
