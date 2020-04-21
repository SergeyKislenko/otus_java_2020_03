package ru.otus;

import ru.otus.annotations.runner.Tester;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
       Tester tester = new Tester();
       tester.doTest("ru.otus.test.NeedTesting");
       tester.doTest("ru.otus.test.NeedTesting");
    }
}
