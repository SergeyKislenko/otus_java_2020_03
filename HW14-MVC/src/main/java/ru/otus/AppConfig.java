package ru.otus;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;
import ru.otus.core.model.Address;
import ru.otus.core.model.Phone;
import ru.otus.core.model.User;
import ru.otus.hibernate.HibernateUtils;

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
}
