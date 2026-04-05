/**
 * Configuration for rate limiting.
 * Encapsulates the limit and time window.
 */
public class RateLimitConfig {
    private final int maxRequests;
    private final long windowSizeMillis;
    
    public RateLimitConfig(int maxRequests, long windowSizeMillis) {
        if (maxRequests <= 0) {
            throw new IllegalArgumentException("maxRequests must be positive");
        }
        if (windowSizeMillis <= 0) {
            throw new IllegalArgumentException("windowSizeMillis must be positive");
        }
        this.maxRequests = maxRequests;
        this.windowSizeMillis = windowSizeMillis;
    }
    
    public int getMaxRequests() {
        return maxRequests;
    }
    
    public long getWindowSizeMillis() {
        return windowSizeMillis;
    }
    
    // Factory methods for common configurations
    public static RateLimitConfig perMinute(int maxRequests) {
        return new RateLimitConfig(maxRequests, 60_000L);
    }
    
    public static RateLimitConfig perHour(int maxRequests) {
        return new RateLimitConfig(maxRequests, 3_600_000L);
    }
    
    public static RateLimitConfig perSecond(int maxRequests) {
        return new RateLimitConfig(maxRequests, 1_000L);
    }
}
