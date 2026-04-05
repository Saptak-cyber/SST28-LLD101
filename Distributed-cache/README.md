# Distributed Cache System - Design Document

## Overview
A distributed cache system that distributes data across multiple cache nodes with pluggable distribution strategies and eviction policies.

## Class Diagram

```
┌─────────────────────────┐
│  DistributedCache       │
│  <<interface>>          │
├─────────────────────────┤
│ + get(key): String      │
│ + put(key, value): void │
└───────────▲─────────────┘
            │
            │ implements
            │
┌───────────┴──────────────────────────────────────┐
│  DistributedCacheImpl                            │
├──────────────────────────────────────────────────┤
│ - nodes: List<CacheNode>                         │
│ - distributionStrategy: DistributionStrategy     │
│ - database: Database                             │
├──────────────────────────────────────────────────┤
│ + get(key): String                               │
│ + put(key, value): void                          │
└────────┬─────────────────────────┬───────────────┘
         │                         │
         │ uses                    │ uses
         │                         │
         ▼                         ▼
┌─────────────────────┐   ┌──────────────────────────┐
│  CacheNode          │   │  DistributionStrategy    │
├─────────────────────┤   │  <<interface>>           │
│ - capacity: int     │   ├──────────────────────────┤
│ - storage: Map      │   │ + getNodeIndex(key,      │
│ - evictionPolicy    │   │     totalNodes): int     │
├─────────────────────┤   └────────▲─────────────────┘
│ + get(key): String  │            │
│ + put(key, value)   │            │ implements
└──────┬──────────────┘            │
       │                   ┌───────┴────────────────┐
       │ uses              │                        │
       │          ┌────────┴──────────┐  ┌─────────┴──────────┐
       │          │ ModuloDistribution│  │ ConsistentHashing  │
       │          │ Strategy          │  │ Strategy (future)  │
       │          └───────────────────┘  └────────────────────┘
       │
       ▼
┌─────────────────────┐
│  EvictionPolicy     │
│  <<interface>>      │
├─────────────────────┤
│ + recordAccess(key) │
│ + evict(): String   │
└────────▲────────────┘
         │
         │ implements
         │
    ┌────┴─────┬──────────┬──────────┐
    │          │          │          │
┌───┴────┐ ┌──┴───┐ ┌────┴────┐ ┌──┴───┐
│  LRU   │ │ MRU  │ │   LFU   │ │ FIFO │
│ Policy │ │(fut.)│ │ (future)│ │(fut.)│
└────────┘ └──────┘ └─────────┘ └──────┘

┌─────────────────────┐
│  Database           │
│  <<interface>>      │
├─────────────────────┤
│ + fetch(key): String│
│ + save(key, value)  │
└────────▲────────────┘
         │
         │ implements
         │
┌────────┴────────────┐
│ InMemoryDatabase    │
└─────────────────────┘
```

## Key Design Decisions

### 1. Data Distribution Across Nodes

The system uses a **DistributionStrategy** interface to determine which cache node stores a given key:

- **Current Implementation**: `ModuloDistributionStrategy` uses `hash(key) % numberOfNodes`
- **Future Extension**: Can easily add `ConsistentHashingStrategy` by implementing the same interface
- **Benefits**: 
  - Decouples distribution logic from cache implementation
  - Easy to swap strategies without changing core code
  - Testable in isolation

**How it works:**
```java
int nodeIndex = distributionStrategy.getNodeIndex(key, nodes.size());
CacheNode node = nodes.get(nodeIndex);
```

### 2. Cache Miss Handling

When `get(key)` is called:

1. Calculate target node using distribution strategy
2. Check if key exists in that node's cache
3. **If cache hit**: Return value from cache
4. **If cache miss**: 
   - Fetch value from database
   - Store in cache (may trigger eviction)
   - Return value

```java
String value = node.get(key);
if (value == null) {
    value = database.fetch(key);
    if (value != null) {
        node.put(key, value);
    }
}
```

### 3. Eviction Policy

Each `CacheNode` has:
- Fixed capacity
- Pluggable `EvictionPolicy` instance

**Current Implementation**: LRU (Least Recently Used)
- Uses `LinkedHashMap` with access-order
- Tracks access order automatically
- Evicts least recently accessed item when capacity is reached

**How eviction works:**
```java
if (storage.size() >= capacity) {
    String evictedKey = evictionPolicy.evict();
    storage.remove(evictedKey);
}
storage.put(key, value);
evictionPolicy.recordAccess(key);
```

### 4. Extensibility

#### Adding New Distribution Strategy
```java
public class ConsistentHashingStrategy implements DistributionStrategy {
    @Override
    public int getNodeIndex(String key, int totalNodes) {
        // Implement consistent hashing logic
    }
}
```

#### Adding New Eviction Policy
```java
public class LFUEvictionPolicy implements EvictionPolicy {
    private Map<String, Integer> frequency = new HashMap<>();
    
    @Override
    public void recordAccess(String key) {
        frequency.merge(key, 1, Integer::sum);
    }
    
    @Override
    public String evict() {
        // Return key with lowest frequency
    }
}
```

#### Usage with Different Strategies
```java
// With consistent hashing and LFU
DistributedCache cache = new DistributedCacheImpl(
    5,                                    // nodes
    100,                                  // capacity
    LFUEvictionPolicy::new,              // eviction policy
    new ConsistentHashingStrategy(),     // distribution
    database
);
```

## Core Components

### DistributedCache (Interface)
Main API for cache operations.

### DistributedCacheImpl
- Manages multiple cache nodes
- Delegates to distribution strategy
- Handles cache miss by fetching from database

### CacheNode
- Individual cache with limited capacity
- Thread-safe operations
- Delegates eviction decisions to policy

### DistributionStrategy (Interface)
- Strategy pattern for node selection
- Implementations: Modulo, Consistent Hashing (future)

### EvictionPolicy (Interface)
- Strategy pattern for eviction
- Implementations: LRU, MRU (future), LFU (future)

### Database (Interface)
- Abstraction for persistent storage
- Assumed to be provided

## Assumptions

1. Keys are unique strings
2. Values are strings (can be generified to `<K, V>`)
3. Database interface is available
4. No real network communication (in-memory simulation)
5. Database is updated on `put()` operations
6. Thread-safety at node level (synchronized methods)

## Running the Demo

```bash
javac *.java
java Demo
```

Expected output shows:
- Cache miss → database fetch
- Cache hit → direct return
- Put operations
- LRU eviction behavior

## Future Enhancements

1. **Consistent Hashing**: Better distribution when nodes are added/removed
2. **Additional Eviction Policies**: MRU, LFU, FIFO
3. **Generics**: Support `<K, V>` instead of `<String, String>`
4. **TTL Support**: Time-based expiration
5. **Cache Statistics**: Hit rate, miss rate, eviction count
6. **Replication**: Data redundancy across nodes
7. **Write-through vs Write-back**: Configurable cache update strategies
