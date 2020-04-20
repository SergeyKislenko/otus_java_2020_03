package ru.otus.test;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class NeedTesting {
    @Before
    public void firstBefore1() {
        System.out.print("Before_1 ");
    }

    @Before
    public void secondBefore2() {
        System.out.print("Before_2 ");
    }

    @Test
    public void firstTest() throws Exception {
       throw new Exception();
    }

    @Test
    public void secondTest() {
        System.out.print("Test_2 ");
    }

    @After
    public void  firstAfter() {
        System.out.print("After_1 ");
    }

    @After
    public void secondAfter() {
        System.out.print("After_2 ");
    }
}
