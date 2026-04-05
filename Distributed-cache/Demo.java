// Demo class to show usage
public class Demo {
    public static void main(String[] args) {
        // Setup database
        Database database = new InMemoryDatabase();
        
        // Pre-populate database
        database.save("user:1", "Alice");
        database.save("user:2", "Bob");
        database.save("user:3", "Charlie");
        
        // Create distributed cache with 3 nodes, capacity 2 per node
        DistributedCache cache = new DistributedCacheImpl(
            3,                              // number of nodes
            2,                              // capacity per node
            LRUEvictionPolicy::new,         // eviction policy factory
            new ModuloDistributionStrategy(), // distribution strategy
            database
        );
        
        System.out.println("=== Distributed Cache Demo ===\n");
        
        // Test cache miss - will fetch from database
        System.out.println("1. Cache miss scenario:");
        String value1 = cache.get("user:1");
        System.out.println("get(user:1) = " + value1 + " (fetched from DB)");
        
        // Test cache hit
        System.out.println("\n2. Cache hit scenario:");
        String value2 = cache.get("user:1");
        System.out.println("get(user:1) = " + value2 + " (from cache)");
        
        // Test put operation
        System.out.println("\n3. Put operation:");
        cache.put("user:4", "David");
        System.out.println("put(user:4, David)");
        String value3 = cache.get("user:4");
        System.out.println("get(user:4) = " + value3 + " (from cache)");
        
        // Test eviction by filling cache
        System.out.println("\n4. Testing LRU eviction:");
        cache.get("user:2");
        cache.get("user:3");
        cache.put("user:5", "Eve");
        cache.put("user:6", "Frank");
        System.out.println("Added multiple entries to trigger eviction");
        
        System.out.println("\n=== Demo Complete ===");
    }
}
