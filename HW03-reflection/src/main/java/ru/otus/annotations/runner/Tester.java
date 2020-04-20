package ru.otus.annotations.runner;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Tester {
    private int allTests = 0;
    private int completedTests = 0;
    private int faultTests = 0;

    public Tester() {
    }

    public void doTest(String className) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object objTestClass;
        Class clazz = Class.forName(className);
        Method[] testMethods = Arrays.stream(clazz.getMethods()).filter(method -> method.isAnnotationPresent(Test.class)).toArray(Method[]::new);
        Method[] beforeMethods = Arrays.stream(clazz.getMethods()).filter(method -> method.isAnnotationPresent(Before.class)).toArray(Method[]::new);
        Method[] afterMethods = Arrays.stream(clazz.getMethods()).filter(method -> method.isAnnotationPresent(After.class)).toArray(Method[]::new);
        allTests = (int) Arrays.stream(clazz.getMethods()).filter(method -> method.isAnnotationPresent(Test.class)).count();

        for (Method method : testMethods) {
                try {
                    objTestClass = clazz.getDeclaredConstructor().newInstance();
                    executeMethodsWithAnnotation(objTestClass, Before.class, beforeMethods);
                    executeMethodsWithAnnotation(objTestClass, Test.class, method);
                    completedTests++;
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    System.out.println("Error while execute test method" + method.getName());
                    faultTests++;
                }finally {
                    executeMethodsWithAnnotation(clazz.getDeclaredConstructor().newInstance(), After.class, afterMethods);
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

    private void printStatistic() {
        System.out.println(String.format("\n====================\nAll tests: %d\nCompleted Tests: %d\nFault Tests: %d\n====================", allTests, completedTests, faultTests));
    }
}

