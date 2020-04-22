package ru.otus.annotations.runner;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Tester {


    public Tester() {
    }

    public void doTest(String className) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        int allTests = 0;
        int completedTests = 0;
        int faultTests = 0;
        Object objTestClass;
        Class clazz = Class.forName(className);
        Method[] testMethods = Arrays.stream(clazz.getMethods()).filter(method -> method.isAnnotationPresent(Test.class)).toArray(Method[]::new);
        Method[] beforeMethods = Arrays.stream(clazz.getMethods()).filter(method -> method.isAnnotationPresent(Before.class)).toArray(Method[]::new);
        Method[] afterMethods = Arrays.stream(clazz.getMethods()).filter(method -> method.isAnnotationPresent(After.class)).toArray(Method[]::new);
        allTests = (int) Arrays.stream(clazz.getMethods()).filter(method -> method.isAnnotationPresent(Test.class)).count();

        for (Method method : testMethods) {
            objTestClass = clazz.getDeclaredConstructor().newInstance();
            try {
                executeMethods(objTestClass, Before.class, beforeMethods);
                executeMethods(objTestClass, Test.class, method);
                executeMethods(objTestClass, After.class, afterMethods);
                completedTests++;
            } catch (IllegalAccessException | InvocationTargetException e) {
                System.out.println("Error while execute test method: " + method.getName());
                faultTests++;
            } finally {
                try {
                    executeMethods(objTestClass, After.class, afterMethods);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    System.out.println("Error while execute After test method " + method.getName() + " in finally block");
                }
            }
        }
        printStatistic(allTests, completedTests, faultTests);
    }

    private static void executeMethods(Object objTestClass, Class<? extends Annotation> annotation, Method... methods) throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            callMethod(objTestClass, method);
        }
    }

    private static void callMethod(Object object, Method method) throws InvocationTargetException, IllegalAccessException {
        method.setAccessible(true);
        method.invoke(object);
    }

    private void printStatistic(int allTests, int completedTests, int faultTests) {
        System.out.println(String.format("\n====================\nAll tests: %d\nCompleted Tests: %d\nFault Tests: %d\n====================", allTests, completedTests, faultTests));
    }
}

