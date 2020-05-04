package ru.otus.bench;

import java.util.ArrayList;
import java.util.List;

public class TestObj implements TestObjMBean {
    private int size = 100000;
    private int countLoop = 0;
    private List<Integer> list = new ArrayList<>();

    public TestObj() {
    }

    @Override
    public void run() throws InterruptedException {
        while (true) {
            for (int idx = 0; idx < size; idx++) {
                list.add(idx * 2);
                countLoop++;
            }
            list.subList(0, size / 2).clear();
            Thread.sleep(150);
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int getCountLoop() {
        return countLoop;
    }
}
