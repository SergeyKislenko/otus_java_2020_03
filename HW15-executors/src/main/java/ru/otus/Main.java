package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private String last = "second";

    private synchronized void action(String message) {
        int index = 1;
        boolean flag = true;

        while (true) {
            try {
                while (last.equals(message)) {
                    this.wait();
                }
                logger.info(String.valueOf(index));
                last = message;
                sleep();
                notifyAll();

                if (flag) {
                    index++;
                } else {
                    index--;
                }

                if (index >= 10) {
                    flag = false;
                } else if (index == 1) {
                    flag = true;
                }

            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new NotInterestingException(ex);
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        new Thread(() -> main.action("first")).start();
        new Thread(() -> main.action("second")).start();
    }

    private static void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private static class NotInterestingException extends RuntimeException {
        NotInterestingException(InterruptedException ex) {
            super(ex);
        }
    }
}
