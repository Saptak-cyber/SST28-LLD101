# Rate Limiting System Design

## Overview
A pluggable, thread-safe rate limiting system for controlling external resource usage in a backend API system.

## Architecture

### Core Components

1. **RateLimiter Interface**
   - Single responsibility: Define contract for rate limiting
   - Open/Closed: Open for extension (new algorithms), closed for modification
   - Dependency Inversion: Clients depend on abstraction, not concrete implementations

2. **RateLimitConfig**
   - Encapsulates configuration (maxRequests, windowSize)
   - Immutable for thread-safety
   - Factory methods for common use cases

3. **Concrete Implementations**
   - FixedWindowCounter
   - SlidingWindowCounter
   - Each implements RateLimiter interface

4. **RateLimiterFactory**
   - Factory pattern for creating rate limiters
   - Enables easy algorithm switching

5. **ExternalResourceGateway**
   - Integration point for business logic
   - Applies rate limiting before external calls
   - Dependency injection of RateLimiter

## Design Decisions

### 1. Interface-Based Design
- **Why**: Allows plugging in new algorithms without changing client code
- **SOLID**: Interface Segregation, Dependency Inversion
- **Benefit**: Easy to test with mocks, easy to extend

### 2. Strategy Pattern
- **Why**: Different rate limiting algorithms are interchangeable strategies
- **Implementation**: RateLimiter interface with multiple implementations
- **Benefit**: Runtime algorithm selection, clean separation of concerns

### 3. Thread-Safety Approach
- **ConcurrentHashMap**: For key-based storage (thread-safe map operations)
- **Synchronized blocks**: For atomic check-and-increment operations
- **AtomicInteger/AtomicLong**: For lock-free counters where possible
- **Trade-off**: Slight performance overhead for correctness

### 4. Key-Based Rate Limiting
- **Why**: Different entities (tenants, customers, API keys) need independent limits
- **Implementation**: Map<String, Data> structure
- **Benefit**: Flexible, supports multi-tenancy

### 5. Separation of Concerns
- Rate limiting logic separate from business logic
- Gateway pattern for integration
- Exception-based flow control for rate limit violations

## Algorithm Comparison

### Fixed Window Counter

**How it works:**
- Time divided into fixed windows (e.g., 0-60s, 60-120s)
- Counter resets at window boundaries
- Simple: increment counter, check if < limit

**Pros:**
- Simple implementation
- Low memory: O(1) per key
- Fast: O(1) operations
- Easy to understand and debug

**Cons:**
- Boundary burst problem: Can allow 2x limit
  - Example: 100 requests at 11:59:59, 100 more at 12:00:01
  - Total: 200 requests in 2 seconds, but both within limits
- Less accurate rate limiting

**Best for:**
- High-throughput systems where approximate limiting is acceptable
- Cost-sensitive scenarios (less memory)
- When simplicity is prioritized

### Sliding Window Counter

**How it works:**
- Maintains log of request timestamps
- Window slides continuously with time
- Removes old timestamps, counts remaining

**Pros:**
- Accurate rate limiting
- No boundary burst problem
- Smooth enforcement across time
- Better user experience

**Cons:**
- Higher memory: O(m) per key, where m = requests in window
- Slower: O(m) cleanup operation per request
- More complex implementation

**Best for:**
- Strict rate limiting requirements
- Paid APIs where accuracy matters
- When preventing burst attacks is critical

## Extensibility

### Adding New Algorithms

To add Token Bucket:

```java
public class TokenBucket implements RateLimiter {
    private final RateLimitConfig config;
    private final ConcurrentHashMap<String, BucketData> buckets;
    
    @Override
    public boolean allowRequest(String key) {
        // Refill tokens based on time elapsed
        // Consume 1 token if available
        // Return true if token consumed
    }
    
    private static class BucketData {
        double tokens;
        long lastRefillTime;
    }
}
```

Update factory:
```java
case TOKEN_BUCKET:
    return new TokenBucket(config);
```

No changes needed in business logic!

## Usage Patterns

### Pattern 1: Per-Tenant Limiting
```java
RateLimiter limiter = new SlidingWindowCounter(
    RateLimitConfig.perMinute(100)
);
gateway = new ExternalResourceGateway(limiter);

// Use tenantId as key
gateway.callExternalResource(tenantId, request, client);
```

### Pattern 2: Per-API-Key Limiting
```java
// Use apiKey as key
gateway.callExternalResource(apiKey, request, client);
```

### Pattern 3: Per-Provider Limiting
```java
// Use provider name as key
gateway.callExternalResource("stripe", request, client);
```

### Pattern 4: Composite Keys
```java
String key = tenantId + ":" + providerId;
gateway.callExternalResource(key, request, client);
```

## Testing Strategy

1. **Unit Tests**: Test each algorithm independently
2. **Thread-Safety Tests**: Concurrent requests from multiple threads
3. **Integration Tests**: Test with ExternalResourceGateway
4. **Performance Tests**: Measure throughput and latency
5. **Boundary Tests**: Test window boundaries, edge cases

## Performance Characteristics

| Algorithm | Time Complexity | Space Complexity | Accuracy |
|-----------|----------------|------------------|----------|
| Fixed Window | O(1) | O(n) | Approximate |
| Sliding Window | O(m) | O(n*m) | Exact |

Where:
- n = number of unique keys
- m = requests per window

## Future Enhancements

1. **Distributed Rate Limiting**: Use Redis for multi-instance deployments
2. **Metrics & Monitoring**: Track rate limit hits, rejections
3. **Dynamic Configuration**: Update limits without restart
4. **Hierarchical Limits**: Global + per-tenant limits
5. **Rate Limit Headers**: Return X-RateLimit-* headers to clients
6. **Graceful Degradation**: Queue requests instead of rejecting
