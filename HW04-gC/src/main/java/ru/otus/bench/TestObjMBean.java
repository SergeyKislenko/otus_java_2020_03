package ru.otus.bench;

public interface TestObjMBean {
    void run() throws InterruptedException;

    int getSize();

    void setSize(int size);

    int getCountLoop();
}
