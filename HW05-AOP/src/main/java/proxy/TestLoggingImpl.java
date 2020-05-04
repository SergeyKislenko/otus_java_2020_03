package proxy;

import annotations.Log;

public class TestLoggingImpl implements TestLogging{
    public TestLoggingImpl() {
    }

    @Override
    @Log
    public void calculation(int a, int b) {
        System.out.println(a + b);
    }

    @Override
    public void calculation(int a, int b, int c) {
        System.out.println(a + b + c);
    }

    @Override
    public void multiplication(int a, int b) {
        System.out.println(a * b);
    }
}
