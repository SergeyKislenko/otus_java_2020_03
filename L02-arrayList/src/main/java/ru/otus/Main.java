package ru.otus;

import ru.otus.arrays.DIYArrayList;

import java.util.Collections;


public class Main {
    public static void main(String[] args) {
        DIYArrayList<Integer> firstArray = new DIYArrayList();
        DIYArrayList<Integer> secondArray = new DIYArrayList();
        DIYArrayList<String> thirdArray = new DIYArrayList();

        for (int i = 1; i <= 25; i++) {
            firstArray.add(i);
        }
        for (int j = 25; j > 0; j--) {
            secondArray.add(j);
        }

        System.out.println("Raw Lists:");
        System.out.println("===========================");
        System.out.println(firstArray);
        System.out.println(secondArray);
        System.out.println(thirdArray);

        Collections.copy(secondArray, firstArray);
        Collections.addAll(thirdArray, "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u");
        Collections.sort(thirdArray, Collections.reverseOrder());

        System.out.println("Lists after using Collection:");
        System.out.println("===========================");
        System.out.println(firstArray);
        System.out.println(secondArray);
        System.out.println(thirdArray);
    }
}
