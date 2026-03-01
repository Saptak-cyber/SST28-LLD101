package com.example.metrics;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Global metrics registry (Singleton).
 *
 * Thread-safe lazy initialization using static holder pattern.
 * Protected against reflection and serialization attacks.
 */
public class MetricsRegistry implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Map<String, Long> counters = new HashMap<>();

    // Private constructor prevents direct instantiation
    private MetricsRegistry() {
        // Block reflection-based multiple construction
        if (Holder.INSTANCE != null) {
            throw new IllegalStateException("Singleton instance already exists. Cannot create another instance.");
        }
    }

    // Static holder pattern for lazy, thread-safe initialization
    private static class Holder {
        private static final MetricsRegistry INSTANCE = new MetricsRegistry();
    }

    // Thread-safe lazy initialization
    public static MetricsRegistry getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized void setCount(String key, long value) {
        counters.put(key, value);
    }

    public synchronized void increment(String key) {
        counters.put(key, getCount(key) + 1);
    }

    public synchronized long getCount(String key) {
        return counters.getOrDefault(key, 0L);
    }

    public synchronized Map<String, Long> getAll() {
        return Collections.unmodifiableMap(new HashMap<>(counters));
    }

    // Preserve singleton on deserialization
    @Serial
    private Object readResolve() {
        return getInstance();
    }
}
