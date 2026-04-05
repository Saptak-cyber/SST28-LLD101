import java.util.HashMap;
import java.util.Map;

// Individual cache node with limited capacity
public class CacheNode {
    private final int capacity;
    private final Map<String, String> storage;
    private final EvictionPolicy evictionPolicy;
    
    public CacheNode(int capacity, EvictionPolicy evictionPolicy) {
        this.capacity = capacity;
        this.storage = new HashMap<>();
        this.evictionPolicy = evictionPolicy;
    }
    
    public synchronized String get(String key) {
        if (storage.containsKey(key)) {
            evictionPolicy.recordAccess(key);
            return storage.get(key);
        }
        return null;
    }
    
    public synchronized void put(String key, String value) {
        // If key already exists, update it
        if (storage.containsKey(key)) {
            storage.put(key, value);
            evictionPolicy.recordAccess(key);
            return;
        }
        
        // If at capacity, evict
        if (storage.size() >= capacity) {
            String evictedKey = evictionPolicy.evict();
            if (evictedKey != null) {
                storage.remove(evictedKey);
            }
        }
        
        // Add new entry
        storage.put(key, value);
        evictionPolicy.recordAccess(key);
    }
    
    public synchronized int size() {
        return storage.size();
    }
}
