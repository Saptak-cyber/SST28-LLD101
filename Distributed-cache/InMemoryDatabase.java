import java.util.HashMap;
import java.util.Map;

// Simple in-memory database implementation for testing
public class InMemoryDatabase implements Database {
    private final Map<String, String> storage = new HashMap<>();
    
    @Override
    public String fetch(String key) {
        return storage.get(key);
    }
    
    @Override
    public void save(String key, String value) {
        storage.put(key, value);
    }
}
