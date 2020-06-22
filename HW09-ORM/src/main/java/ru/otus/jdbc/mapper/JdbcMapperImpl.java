package ru.otus.jdbc.mapper;

import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.mapper.interfaces.EntityClassMetaData;
import ru.otus.jdbc.mapper.interfaces.EntitySQLMetaData;
import ru.otus.jdbc.mapper.interfaces.JdbcMapper;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {
    private final DbExecutorImpl<T> dbExecutor;
    private final SessionManagerJdbc sessionManager;

    public JdbcMapperImpl(SessionManagerJdbc sessionManager) {
        dbExecutor = new DbExecutorImpl<>();
        this.sessionManager = sessionManager;
    }

    @Override
    public void insert(T objectData) {
        EntityClassMetaDataImpl<T> entityClassMetaData = new EntityClassMetaDataImpl(objectData.getClass());
        EntitySQLMetaData entitySQLMetaData = new EntitySQLMetaDataImpl<>(entityClassMetaData);
        String insert = entitySQLMetaData.getInsertSql();
        sessionManager.beginSession();
        try {
            dbExecutor.executeInsert(getConnection(), insert, getParams(objectData));
            sessionManager.commitSession();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(T objectData) {
        EntityClassMetaDataImpl<T> entityClassMetaData = new EntityClassMetaDataImpl(objectData.getClass());
        EntitySQLMetaData entitySQLMetaData = new EntitySQLMetaDataImpl<>(entityClassMetaData);
        Field idField = entityClassMetaData.getIdField();
        String update = entitySQLMetaData.getUpdateSql();
        idField.setAccessible(true);
        Object id = null;
        try {
            id = idField.get(objectData);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        sessionManager.beginSession();
        List<Object> params = getParams(objectData);
        params.add(id);
        try {
            dbExecutor.executeUpdate(getConnection(), update, params);
            sessionManager.commitSession();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void insertOrUpdate(T objectData) {
        EntityClassMetaDataImpl<T> entityClassMetaData = new EntityClassMetaDataImpl(objectData.getClass());
        Field idField = entityClassMetaData.getIdField();
        idField.setAccessible(true);
        T object = null;
        try {
            object = findById((long) idField.get(objectData), (Class<T>) objectData.getClass());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (object == null) {
            insert(objectData);
            return;
        }

        update(objectData);
    }

    @Override
    public T findById(long id, Class<T> clazz) {
        EntityClassMetaDataImpl entityClassMetaData = new EntityClassMetaDataImpl(clazz);
        EntitySQLMetaData entitySQLMetaData = new EntitySQLMetaDataImpl<>(entityClassMetaData);
        String select = entitySQLMetaData.getSelectByIdSql();
        sessionManager.beginSession();
        Optional<T> findObject = dbExecutor.executeSelect(getConnection(), select, id, resultSet -> {
            try {
                if (resultSet.next()) {
                    return createNewObject(resultSet, clazz);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return null;
        });
        return findObject.orElse(null);
    }

    private List<Object> getParams(T object) {
        EntityClassMetaData<T> entityClassMetaData = new EntityClassMetaDataImpl(object.getClass());
        List<Object> listParams = new ArrayList<>();
        for (Field field : entityClassMetaData.getFieldsWithoutId()) {
            field.setAccessible(true);
            try {
                listParams.add(field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return listParams;
    }

    private T createNewObject(ResultSet resultSet, Class<T> clazz) {
        EntityClassMetaData<T> entityClassMetaData = new EntityClassMetaDataImpl<>(clazz);
        Constructor<T> constructor = entityClassMetaData.getConstructor();
        try {
            T object = constructor.newInstance();
            for (Field field : entityClassMetaData.getAllFields()) {
                field.setAccessible(true);
                field.set(object, resultSet.getObject(field.getName()));
            }
            return object;
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
