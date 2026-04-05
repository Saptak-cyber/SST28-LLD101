# Pluggable Rate Limiting System

A production-ready, thread-safe rate limiting system for controlling external resource usage in Java backend systems.

## Features

- Multiple rate limiting algorithms (Fixed Window, Sliding Window)
- Pluggable architecture - easy to add new algorithms
- Thread-safe for concurrent environments
- Key-based limiting (per tenant, customer, API key, etc.)
- Clean separation from business logic
- SOLID principles and OOP best practices

## Quick Start

### Basic Usage

```java
// 1. Create configuration
RateLimitConfig config = RateLimitConfig.perMinute(100);

// 2. Create rate limiter
RateLimiter limiter = new SlidingWindowCounter(config);

// 3. Create gateway
ExternalResourceGateway gateway = new ExternalResourceGateway(limiter);

// 4. Use in your service
try {
    String result = gateway.callExternalResource(
        tenantId,
        request,
        this::callExternalApi
    );
} catch (RateLimitExceededException e) {
    // Handle rate limit exceeded
}
```

### Switching Algorithms

```java
// Use Fixed Window
RateLimiter limiter = RateLimiterFactory.create(
    RateLimiterFactory.Algorithm.FIXED_WINDOW,
    config
);

// Switch to Sliding Window - no code changes needed!
RateLimiter limiter = RateLimiterFactory.create(
    RateLimiterFactory.Algorithm.SLIDING_WINDOW,
    config
);
```

## Running the Demo

```bash
javac *.java
java Demo
```

## Running Tests

```bash
javac *.java
java RateLimiterTest
```

## Architecture

See [DESIGN.md](DESIGN.md) for detailed design decisions and trade-offs.

## Key Classes

- `RateLimiter` - Core interface
- `FixedWindowCounter` - Simple, fast algorithm
- `SlidingWindowCounter` - Accurate, smooth algorithm
- `ExternalResourceGateway` - Integration point for business logic
- `RateLimiterFactory` - Factory for creating rate limiters

## Configuration Examples

```java
// 100 requests per minute
RateLimitConfig.perMinute(100)

// 1000 requests per hour
RateLimitConfig.perHour(1000)

// 10 requests per second
RateLimitConfig.perSecond(10)

// Custom window
new RateLimitConfig(500, 300_000) // 500 requests per 5 minutes
```

## Thread Safety

All implementations are thread-safe and can handle concurrent requests from multiple threads.

## License

MIT
