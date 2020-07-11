package ru.otus.cachehw;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    private final Map<K, V> cache = new WeakHashMap<>();
    private List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        if (key != null) {
            cache.put(key, value);
            listeners.forEach(listener -> listener.notify(key, value, "put"));
        }
    }

    @Override
    public void remove(K key) {
        if (key != null) {
            cache.remove(key);
            listeners.forEach(listener -> listener.notify(key, null, "remove"));
        }
    }

    @Override
    public V get(K key) {
        if (key != null) {
            return cache.get(key);
        }
        return null;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }

}
