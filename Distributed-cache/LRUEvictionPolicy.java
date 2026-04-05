import java.util.LinkedHashMap;
import java.util.Map;

// LRU eviction policy implementation
public class LRUEvictionPolicy implements EvictionPolicy {
    private final LinkedHashMap<String, Boolean> accessOrder;
    
    public LRUEvictionPolicy() {
        this.accessOrder = new LinkedHashMap<>(16, 0.75f, true);
    }
    
    @Override
    public void recordAccess(String key) {
        accessOrder.put(key, true);
    }
    
    @Override
    public String evict() {
        if (accessOrder.isEmpty()) {
            return null;
        }
        String lruKey = accessOrder.keySet().iterator().next();
        accessOrder.remove(lruKey);
        return lruKey;
    }
    
    public void remove(String key) {
        accessOrder.remove(key);
    }
}
