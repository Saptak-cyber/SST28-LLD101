// Main distributed cache interface
public interface DistributedCache {
    String get(String key);
    void put(String key, String value);
}
