package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.HwListener;
import ru.otus.cachehw.MyCache;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.Address;
import ru.otus.core.model.Phone;
import ru.otus.core.model.User;
import ru.otus.core.service.DBInitServise;
import ru.otus.core.service.DBInitServiseImpl;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.server.AdminWebServer;
import ru.otus.server.AdminWebServerWithFilterBasedSecurity;
import ru.otus.services.AdminAuthService;
import ru.otus.services.AdminAuthServiceImpl;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class, Address.class, Phone.class);
        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        UserDao userDao = new UserDaoHibernate(sessionManager);
        HwCache<String, User> cache = new MyCache<>();
        DBInitServise dbInitServise = new DBInitServiseImpl();
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao, cache);

        dbInitServise.initUserDb(dbServiceUser);
        HwListener<String, User> cacheEventListener = new HwListener<String, User>() {
            @Override
            public void notify(String key, User value, String action) {
                logger.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };
        cache.addListener(cacheEventListener);

        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        AdminAuthService authService = new AdminAuthServiceImpl(dbServiceUser);

        AdminWebServer adminWebServer = new AdminWebServerWithFilterBasedSecurity(WEB_SERVER_PORT,
                authService, dbServiceUser, gson, templateProcessor);

        adminWebServer.start();
        adminWebServer.join();
    }
}
