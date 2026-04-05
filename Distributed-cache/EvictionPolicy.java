// Strategy interface for eviction policies
public interface EvictionPolicy {
    void recordAccess(String key);
    String evict();
}
