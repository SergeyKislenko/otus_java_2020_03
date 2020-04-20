package ru.otus;

import ru.otus.annotations.runner.Tester;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
       new Tester().doTest("ru.otus.test.NeedTesting");
    }
}
