package ru.otus;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.Address;
import ru.otus.core.model.Phone;
import ru.otus.core.model.User;
import ru.otus.core.service.DBInitServise;
import ru.otus.core.service.DBInitServiseImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

@Configuration
public class AppConfig {

    @Bean
    public <K, V> HwCache<K, V> cache() {
        return new MyCache<>();
    }

    @Bean
    public SessionFactory buildSessionFactory() {
        SessionFactory sessionFactory = HibernateUtils
                .buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);
        return sessionFactory;
    }

    @Bean(initMethod = "initUserDb")
    public DBInitServise dbInitServise() {
        UserDao userDao = new UserDaoHibernate(new SessionManagerHibernate(buildSessionFactory()));
        return new DBInitServiseImpl(userDao, cache());
    }
}
