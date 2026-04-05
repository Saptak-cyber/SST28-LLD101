/**
 * Core interface for rate limiting strategies.
 * Allows plugging in different algorithms without changing client code.
 */
public interface RateLimiter {
    /**
     * Checks if a request is allowed under the current rate limit.
     * 
     * @param key The identifier for rate limiting (e.g., customerId, tenantId, apiKey)
     * @return true if the request is allowed, false otherwise
     */
    boolean allowRequest(String key);
    
    /**
     * Resets the rate limit for a specific key.
     * Useful for testing or manual intervention.
     * 
     * @param key The identifier to reset
     */
    void reset(String key);
}
