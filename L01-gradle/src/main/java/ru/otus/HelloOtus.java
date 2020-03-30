package ru.otus;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;

public class HelloOtus {
    public static void main(String... args) {
        List<String> cities = Lists.newArrayList(null, "Athens", "Berlin", "Brussels", null, "Budapest", "Karachi", "Lisbon");
        Iterables.removeIf(cities, Predicates.isNull());
        System.out.println(cities);
    }
}
