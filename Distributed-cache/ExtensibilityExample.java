// Example showing how to extend the system with new strategies

// Example 1: MRU (Most Recently Used) Eviction Policy
class MRUEvictionPolicy implements EvictionPolicy {
    private String mostRecentKey;
    
    @Override
    public void recordAccess(String key) {
        mostRecentKey = key;
    }
    
    @Override
    public String evict() {
        return mostRecentKey;
    }
}

// Example 2: Consistent Hashing Distribution Strategy
class ConsistentHashingStrategy implements DistributionStrategy {
    private static final int VIRTUAL_NODES = 150;
    
    @Override
    public int getNodeIndex(String key, int totalNodes) {
        // Simplified consistent hashing
        // In production, use a proper consistent hash ring
        int hash = key.hashCode();
        int virtualNode = Math.abs(hash) % (totalNodes * VIRTUAL_NODES);
        return virtualNode / VIRTUAL_NODES;
    }
}

// Example 3: Range-based Distribution Strategy
class RangeBasedDistributionStrategy implements DistributionStrategy {
    @Override
    public int getNodeIndex(String key, int totalNodes) {
        // Distribute based on key prefix ranges
        char firstChar = key.charAt(0);
        int range = ('z' - 'a' + 1) / totalNodes;
        return Math.min((firstChar - 'a') / range, totalNodes - 1);
    }
}

// Usage examples
public class ExtensibilityExample {
    public static void main(String[] args) {
        Database db = new InMemoryDatabase();
        
        // Example 1: Using MRU eviction
        System.out.println("=== Using MRU Eviction Policy ===");
        DistributedCache cacheMRU = new DistributedCacheImpl(
            3, 2, 
            MRUEvictionPolicy::new,
            new ModuloDistributionStrategy(),
            db
        );
        
        // Example 2: Using Consistent Hashing
        System.out.println("\n=== Using Consistent Hashing ===");
        DistributedCache cacheConsistent = new DistributedCacheImpl(
            5, 10,
            LRUEvictionPolicy::new,
            new ConsistentHashingStrategy(),
            db
        );
        
        // Example 3: Using Range-based Distribution
        System.out.println("\n=== Using Range-based Distribution ===");
        DistributedCache cacheRange = new DistributedCacheImpl(
            4, 5,
            LRUEvictionPolicy::new,
            new RangeBasedDistributionStrategy(),
            db
        );
        
        System.out.println("\nAll configurations created successfully!");
        System.out.println("This demonstrates the pluggable architecture.");
    }
}
