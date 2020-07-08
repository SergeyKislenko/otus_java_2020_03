package ru.otus;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.Address;
import ru.otus.core.model.Phone;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.List;
import java.util.Optional;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        User user = new User();
        user.setName("Misha");

        Address address = new Address();
        address.setStreet("st. Lenina");
        user.setAddress(address);

        Phone phone = new Phone();
        phone.setNumber("+7(432)4432");
        phone.setUser(user);
        user.setPhones(List.of(phone));

        long id = dbServiceUser.saveUser(user);
        Optional<User> getUser = dbServiceUser.getUser(id);
        getUser.ifPresentOrElse(System.out::println, () -> logger.info("User does not exist"));

        user.setName("Ibragim");
        id = dbServiceUser.saveUser(user);
        Optional<User> getUser2 = dbServiceUser.getUser(id);
        getUser2.ifPresentOrElse(System.out::println, () -> logger.info("User does not exist"));
    }
}
