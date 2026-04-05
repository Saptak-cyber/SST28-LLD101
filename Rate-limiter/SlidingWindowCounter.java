import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Sliding Window Counter implementation.
 * 
 * Maintains a log of request timestamps and slides the window continuously.
 * More accurate than fixed window but uses more memory.
 * 
 * Trade-offs:
 * + Accurate rate limiting without boundary issues
 * + Smooth enforcement across time
 * - Higher memory usage (O(n*m) where m = requests per window)
 * - Slower operations due to timestamp cleanup
 * + Still prevents burst attacks effectively
 */
public class SlidingWindowCounter implements RateLimiter {
    private final RateLimitConfig config;
    private final ConcurrentHashMap<String, ConcurrentLinkedDeque<Long>> requestLog;
    
    public SlidingWindowCounter(RateLimitConfig config) {
        this.config = config;
        this.requestLog = new ConcurrentHashMap<>();
    }
    
    @Override
    public boolean allowRequest(String key) {
        long now = System.currentTimeMillis();
        ConcurrentLinkedDeque<Long> timestamps = requestLog.computeIfAbsent(
            key, 
            k -> new ConcurrentLinkedDeque<>()
        );
        
        synchronized (timestamps) {
            // Remove timestamps outside the current window
            long windowStart = now - config.getWindowSizeMillis();
            while (!timestamps.isEmpty() && timestamps.peekFirst() <= windowStart) {
                timestamps.pollFirst();
            }
            
            // Check if we're under the limit
            if (timestamps.size() < config.getMaxRequests()) {
                timestamps.addLast(now);
                return true;
            }
            
            return false;
        }
    }
    
    @Override
    public void reset(String key) {
        requestLog.remove(key);
    }
}
