import proxy.Ioc;
import proxy.TestLogging;

public class Main {
    public static void main(String[] args) {
        TestLogging testLogging = Ioc.create();
        testLogging.calculation(1, 2);
        testLogging.multiplication(2, 2);
    }
}
