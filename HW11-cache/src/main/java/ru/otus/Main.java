package ru.otus;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwListener;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.Address;
import ru.otus.core.model.Phone;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.core.service.DbServiceUserWithoutCacheImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUserWithoutCache = new DbServiceUserWithoutCacheImpl(userDao);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
        dbServiceUser.getCache().addListener(new HwListener<Long, User>() {
            @Override
            public void notify(Long key, User value, String action) {
                logger.info("key:{}, value:{}, action: {}", key, value, action);
            }
        });



//219
          long withCache = work(dbServiceUser);
          System.out.println("All time with cache " + withCache);

//380
//        long withoutCache = work(dbServiceUserWithoutCache);
//        System.out.println("All time without cache " + withoutCache);
    }

    private static long work(DBServiceUser dbServiceUser) {
        long beginTime = System.currentTimeMillis();
        List<User> list = new ArrayList<>();
        for (int i = 0; i <= 30; i++) {
            User user = new User();
            user.setName("Misha" + i);
            list.add(user);
        }
        list.stream().forEach(u -> dbServiceUser.saveUser(u));
        list.stream().forEach(u -> dbServiceUser.getUser(u.getId()));
        long endTime = System.currentTimeMillis();
        return endTime - beginTime;
    }
}
