package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AccountDao;
import ru.otus.core.dao.UserDao;
import ru.otus.core.dao.impl.AccountDaoImpl;
import ru.otus.core.dao.impl.UserDaoImpl;
import ru.otus.core.model.Account;
import ru.otus.core.model.User;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        var dataSource = new DataSourceH2();
        var demo = new Main();

        demo.createUserTable(dataSource);
        demo.createAccountTable(dataSource);

        var sessionManager = new SessionManagerJdbc(dataSource);
        UserDao userDao = new UserDaoImpl(sessionManager);
        AccountDao accountDao = new AccountDaoImpl(sessionManager);


//      USER
        User misha = new User(1, "Misha", 18);
        userDao.insertUser(misha);

        Optional<User> findMisha = userDao.findById(misha.getId());
        logger.info(findMisha.toString());

        User ben = new User(2, "Ben", 19);
        userDao.insertOrUpdate(ben);
        logger.info(userDao.findById(ben.getId()).toString());

        ben.setAge(33);
        ben.setName("beN");
        userDao.insertOrUpdate(ben);
        logger.info(userDao.findById(ben.getId()).toString());

        ben.setName("BEN");
        ben.setAge(88);
        userDao.update(ben);

        logger.info(userDao.findById(ben.getId()).toString());

//      ACCOUNT
        Account account = new Account(1, "admin", BigDecimal.valueOf(777));
        accountDao.insertAccount(account);

        Optional<Account> findAccount = accountDao.findById(account.getNo());
        logger.info(findAccount.toString());

        Account newAccount = new Account(2, "regAdmin", BigDecimal.valueOf(666));
        accountDao.insertOrUpdate(newAccount);
        logger.info(accountDao.findById(newAccount.getNo()).toString());

        newAccount.setType("alfaAdmin");
        newAccount.setRest(BigDecimal.valueOf(555));
        accountDao.insertOrUpdate(newAccount);
        logger.info(accountDao.findById(newAccount.getNo()).toString());

        newAccount.setType("goodAdmin");
        newAccount.setRest(BigDecimal.valueOf(444));
        accountDao.update(newAccount);

        logger.info(accountDao.findById(newAccount.getNo()).toString());
    }

    private void createUserTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(50), age int(3))")) {
            pst.executeUpdate();
        }
        System.out.println("table user created");
    }

    private void createAccountTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement("create table account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number)")) {
            pst.executeUpdate();
        }
        System.out.println("table account created");
    }
}
