package ru.otus.jdbc.mapper;

import ru.otus.jdbc.annotations.Id;
import ru.otus.jdbc.mapper.interfaces.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData {
    private final Class<T> clazz;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Field getIdField() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .get();
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(clazz.getDeclaredFields())
                .collect(Collectors.toList());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .collect(Collectors.toList());
    }
}
