package department.observer;

import java.util.ArrayList;
import java.util.List;

public class EventProducer {

    private final List<Listener> listeners = new ArrayList<>();

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    public void event() {
        listeners.forEach(listener -> listener.onUpdate());
    }
}
