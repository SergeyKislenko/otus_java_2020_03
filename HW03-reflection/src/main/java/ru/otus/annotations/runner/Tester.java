package ru.otus.annotations.runner;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Tester {
    private static final String PACKAGE_PATH = "ru.otus.test.";

    private static int completedTests = 0;
    private static int faultTests = 0;
    private static int allTests = 0;

    public Tester() {
    }

    public static void doTest(String className) throws ClassNotFoundException {
            Object objTestClass;
            Class clazz = Class.forName(PACKAGE_PATH + className);
            Method[] methodsPublic = clazz.getMethods();
            allTests = (int) Arrays.stream(methodsPublic).filter(method -> method.isAnnotationPresent(Test.class)).count();

            for (Method method : methodsPublic) {
                if (method.isAnnotationPresent(Test.class)) {
                    try {
                        objTestClass = clazz.getDeclaredConstructor().newInstance();
                        executeMethodsWithAnnotation(objTestClass, Before.class, methodsPublic);
                        executeMethodsWithAnnotation(objTestClass, Test.class, method);
                        executeMethodsWithAnnotation(objTestClass, After.class, methodsPublic);
                        completedTests++;
                    }  catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                        System.out.println("Error while execute test method" + method.getName());
                        faultTests++;
                    }
                }
            }
        printStatistic();
    }

    private static void executeMethodsWithAnnotation(Object objTestClass, Class<? extends Annotation> annotation, Method... methods) throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                callMethod(objTestClass, method);
            }
        }
    }

    private static void callMethod(Object object, Method method) throws InvocationTargetException, IllegalAccessException {
        method.setAccessible(true);
        method.invoke(object);
    }

    private static void printStatistic(){
        System.out.println(String.format("\n====================\nAll tests: %d\nCompleted Tests: %d\nFault Tests: %d\n====================", allTests, completedTests, faultTests));
    }
}

