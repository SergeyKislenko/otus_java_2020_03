package ru.otus.servlet;

import com.google.gson.Gson;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;


public class AdminApiServlet extends HttpServlet {

    private final DBServiceUser dbServiceUser;
    private final Gson gson;

    public AdminApiServlet(DBServiceUser dbServiceUser, Gson gson) {
        this.dbServiceUser = dbServiceUser;
        this.gson = gson;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        User user = gson.fromJson(body, User.class);
        dbServiceUser.saveUser(user);
    }


}
