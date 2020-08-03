package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

@Component
public class MyCache<K, V> implements HwCache<K, V> {
    private static final Logger logger = LoggerFactory.getLogger(MyCache.class);
    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();
    private static final String PUT = "put";
    private static final String REMOVE = "remove";

    @Override
    public void put(K key, V value) {
        if (key != null) {
            cache.put(key, value);
            notifyListeners(key, value, PUT);
        }
    }

    @Override
    public void remove(K key) {
        if (key != null) {
            cache.remove(key);
            notifyListeners(key, null, REMOVE);
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

    private void notifyListeners(K key, V value, String action) {
        listeners.forEach(listener -> {
            try {
                listener.notify(key, value, action);
            } catch (Exception e) {
                logger.error("Listener call error: {}", e.getMessage());
            }
        });
    }
}
