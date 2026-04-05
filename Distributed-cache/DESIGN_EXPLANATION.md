# Distributed Cache - Detailed Design Explanation

## Architecture Overview

The system follows SOLID principles with a focus on extensibility through the Strategy pattern.

## Data Flow Diagrams

### GET Operation Flow

```
User calls get("user:1")
        ↓
DistributedCacheImpl
        ↓
DistributionStrategy.getNodeIndex("user:1", 3) → returns 1
        ↓
CacheNode[1].get("user:1")
        ↓
    ┌───────────────┐
    │ Key in cache? │
    └───────┬───────┘
            │
    ┌───────┴────────┐
    │ YES            │ NO
    ↓                ↓
Return value    Database.fetch("user:1")
                     ↓
                Store in CacheNode[1]
                     ↓
                (May trigger eviction)
                     ↓
                Return value
```

### PUT Operation Flow

```
User calls put("user:1", "Alice")
        ↓
Database.save("user:1", "Alice")
        ↓
DistributionStrategy.getNodeIndex("user:1", 3) → returns 1
        ↓
CacheNode[1].put("user:1", "Alice")
        ↓
    ┌──────────────────┐
    │ At capacity?     │
    └────────┬─────────┘
             │
    ┌────────┴─────────┐
    │ YES              │ NO
    ↓                  ↓
EvictionPolicy.evict() → "user:5"   Store directly
    ↓
Remove "user:5" from storage
    ↓
Store "user:1" → "Alice"
    ↓
EvictionPolicy.recordAccess("user:1")
```

## Distribution Strategy Deep Dive

### Modulo Distribution
```
Key: "user:123"
Hash: key.hashCode() = 123456789
Node Index: |123456789| % 3 = 0

Distribution:
Node 0: user:123, user:126, user:129...
Node 1: user:124, user:127, user:130...
Node 2: user:125, user:128, user:131...
```

**Pros:**
- Simple and fast
- Uniform distribution

**Cons:**
- Adding/removing nodes requires rehashing most keys
- Not resilient to node failures

### Consistent Hashing (Future)
```
Hash Ring: 0 ─────────────────────────────── 2^32-1
           │    │         │         │        │
         Node0 Node1    Node2    Node0'   Node1'
                      (virtual nodes)

Key "user:123" → hash → position on ring → nearest node
```

**Pros:**
- Adding/removing nodes affects only neighboring keys
- Better for dynamic scaling

**Cons:**
- More complex implementation
- Requires virtual nodes for balance

## Eviction Policy Deep Dive

### LRU Implementation Details

Uses `LinkedHashMap` with access-order mode:

```java
LinkedHashMap<String, Boolean> accessOrder = 
    new LinkedHashMap<>(16, 0.75f, true);
                                    ↑
                            access-order mode
```

**Access Pattern Example:**
```
Initial: []
put(A) → [A]
put(B) → [A, B]
get(A) → [B, A]  // A moved to end
put(C) → [B, A, C]
put(D) → [A, C, D]  // B evicted (least recently used)
```

**Time Complexity:**
- recordAccess: O(1)
- evict: O(1)
- Space: O(n) where n = capacity

### Future Eviction Policies

#### LFU (Least Frequently Used)
```java
Map<String, Integer> frequency;

recordAccess(key):
    frequency[key]++

evict():
    return key with min(frequency[key])
```

#### MRU (Most Recently Used)
```java
String mostRecent;

recordAccess(key):
    mostRecent = key

evict():
    return mostRecent
```

## Thread Safety

### Current Implementation
- `CacheNode` methods are `synchronized`
- Ensures thread-safe access to individual nodes
- Coarse-grained locking

### Potential Improvements
```java
// Fine-grained locking with ReadWriteLock
class CacheNode {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    
    public String get(String key) {
        lock.readLock().lock();
        try {
            // read operation
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public void put(String key, String value) {
        lock.writeLock().lock();
        try {
            // write operation
        } finally {
            lock.writeLock().unlock();
        }
    }
}
```

## Capacity Management

### Per-Node Capacity
```
Total Cache Capacity = numberOfNodes × capacityPerNode

Example:
- 3 nodes × 100 capacity = 300 total entries
- 5 nodes × 50 capacity = 250 total entries
```

### Eviction Trigger
```java
if (storage.size() >= capacity) {
    String evictedKey = evictionPolicy.evict();
    storage.remove(evictedKey);
}
```

## Extension Points

### 1. Adding Cache Statistics
```java
class CacheNode {
    private long hits = 0;
    private long misses = 0;
    
    public String get(String key) {
        if (storage.containsKey(key)) {
            hits++;
            return storage.get(key);
        }
        misses++;
        return null;
    }
    
    public double getHitRate() {
        return (double) hits / (hits + misses);
    }
}
```

### 2. Adding TTL (Time To Live)
```java
class CacheEntry {
    String value;
    long expiryTime;
    
    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

class CacheNode {
    private Map<String, CacheEntry> storage;
    
    public String get(String key) {
        CacheEntry entry = storage.get(key);
        if (entry != null && !entry.isExpired()) {
            return entry.value;
        }
        storage.remove(key);
        return null;
    }
}
```

### 3. Adding Replication
```java
class DistributedCacheImpl {
    private int replicationFactor = 2;
    
    public void put(String key, String value) {
        int primaryNode = distributionStrategy.getNodeIndex(key, nodes.size());
        
        // Store in primary
        nodes.get(primaryNode).put(key, value);
        
        // Store in replicas
        for (int i = 1; i < replicationFactor; i++) {
            int replicaNode = (primaryNode + i) % nodes.size();
            nodes.get(replicaNode).put(key, value);
        }
    }
}
```

## Performance Characteristics

### Time Complexity
- `get(key)`: O(1) average case
  - Distribution: O(1)
  - Node lookup: O(1)
  - Cache hit: O(1)
  - Cache miss: O(1) + DB fetch time

- `put(key, value)`: O(1) average case
  - Distribution: O(1)
  - Node lookup: O(1)
  - Eviction: O(1) for LRU
  - Storage: O(1)

### Space Complexity
- Total: O(n × c) where n = nodes, c = capacity per node
- Per node: O(c)
- Eviction policy overhead: O(c)

## Testing Strategy

### Unit Tests
```java
@Test
public void testCacheMiss() {
    // Verify database fetch on miss
}

@Test
public void testCacheHit() {
    // Verify no database call on hit
}

@Test
public void testEviction() {
    // Fill cache beyond capacity
    // Verify LRU item is evicted
}

@Test
public void testDistribution() {
    // Verify keys distributed across nodes
}
```

### Integration Tests
```java
@Test
public void testMultipleNodes() {
    // Test with different node counts
}

@Test
public void testConcurrency() {
    // Multiple threads accessing cache
}
```

## Design Patterns Used

1. **Strategy Pattern**: DistributionStrategy, EvictionPolicy
2. **Factory Pattern**: Supplier<EvictionPolicy> for creating policies
3. **Interface Segregation**: Small, focused interfaces
4. **Dependency Injection**: Dependencies passed via constructor
5. **Template Method**: CacheNode delegates to policy

## Trade-offs

### Modulo vs Consistent Hashing
| Aspect | Modulo | Consistent Hashing |
|--------|--------|-------------------|
| Simplicity | ✓ Simple | ✗ Complex |
| Performance | ✓ Fast | ✓ Fast |
| Scalability | ✗ Poor | ✓ Good |
| Rebalancing | ✗ Full rehash | ✓ Minimal |

### LRU vs LFU
| Aspect | LRU | LFU |
|--------|-----|-----|
| Implementation | ✓ Simple | ✗ Complex |
| Memory | ✓ Low | ✗ Higher |
| Temporal locality | ✓ Good | ✗ Poor |
| Frequency patterns | ✗ Ignores | ✓ Captures |

## Conclusion

This design provides a solid foundation for a distributed cache with clear extension points for future enhancements. The use of interfaces and strategy patterns ensures the system remains flexible and maintainable.
