/**
 * Factory for creating rate limiter instances.
 * Follows Factory pattern for easy algorithm switching.
 */
public class RateLimiterFactory {
    
    public enum Algorithm {
        FIXED_WINDOW,
        SLIDING_WINDOW
    }
    
    public static RateLimiter create(Algorithm algorithm, RateLimitConfig config) {
        switch (algorithm) {
            case FIXED_WINDOW:
                return new FixedWindowCounter(config);
            case SLIDING_WINDOW:
                return new SlidingWindowCounter(config);
            default:
                throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }
    }
}
