package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.model.Account;
import ru.otus.core.model.User;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.mapper.JdbcMapperImpl;
import ru.otus.jdbc.mapper.interfaces.JdbcMapper;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        var dataSource = new DataSourceH2();
        var demo = new Main();

        demo.createUserTable(dataSource);
        demo.createAccountTable(dataSource);

        var sessionManager = new SessionManagerJdbc(dataSource);
        JdbcMapper<User> jdbcUserMapper = new JdbcMapperImpl(sessionManager);
        JdbcMapper<Account> jdbcAccountMapper = new JdbcMapperImpl(sessionManager);

//      USER
        User misha = new User(1, "Misha", 18);
        jdbcUserMapper.insert(misha);

        User findMisha = jdbcUserMapper.findById(misha.getId(), User.class);
        logger.info(findMisha.toString());

        User ben = new User(2, "Ben", 19);
        jdbcUserMapper.insertOrUpdate(ben);
        logger.info(jdbcUserMapper.findById(ben.getId(), User.class).toString());

        ben.setAge(33);
        ben.setName("beN");
        jdbcUserMapper.insertOrUpdate(ben);
        logger.info(jdbcUserMapper.findById(ben.getId(), User.class).toString());

        ben.setName("BEN");
        ben.setAge(88);
        jdbcUserMapper.update(ben);

        logger.info(jdbcUserMapper.findById(ben.getId(), User.class).toString());

//      ACCOUNT
        Account account = new Account(1, "admin", BigDecimal.valueOf(777));
        jdbcAccountMapper.insert(account);

        Account findAccount = jdbcAccountMapper.findById(account.getNo(), Account.class);
        logger.info(findAccount.toString());

        Account newAccount = new Account(2, "regAdmin", BigDecimal.valueOf(666));
        jdbcAccountMapper.insertOrUpdate(newAccount);
        logger.info(jdbcAccountMapper.findById(newAccount.getNo(), Account.class).toString());

        newAccount.setType("alfaAdmin");
        newAccount.setRest(BigDecimal.valueOf(555));
        jdbcAccountMapper.insertOrUpdate(newAccount);
        logger.info(jdbcAccountMapper.findById(newAccount.getNo(), Account.class).toString());

        newAccount.setType("goodAdmin");
        newAccount.setRest(BigDecimal.valueOf(444));
        jdbcAccountMapper.update(newAccount);

        logger.info(jdbcAccountMapper.findById(newAccount.getNo(), Account.class).toString());
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
