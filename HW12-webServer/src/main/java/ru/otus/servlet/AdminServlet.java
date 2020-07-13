package ru.otus.servlet;

import ru.otus.core.service.DBServiceUser;
import ru.otus.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AdminServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "adminPanel.ftl";
    private static final String TEMPLATE_ATTR_RANDOM_USER = "allUsers";

    private final DBServiceUser dbServiceUser;
    private final TemplateProcessor templateProcessor;

    public AdminServlet(TemplateProcessor templateProcessor, DBServiceUser dbServiceUser) {
        this.templateProcessor = templateProcessor;
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        dbServiceUser.findAll().ifPresent(allUsers -> paramsMap.put(TEMPLATE_ATTR_RANDOM_USER, allUsers));

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

}
