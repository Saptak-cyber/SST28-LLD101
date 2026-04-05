import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Fixed Window Counter implementation.
 * 
 * Divides time into fixed windows and counts requests per window.
 * Simple and memory efficient, but can allow bursts at window boundaries.
 * 
 * Trade-offs:
 * + Simple implementation
 * + Low memory usage (O(n) where n = number of keys)
 * + Fast O(1) operations
 * - Boundary burst problem: can allow 2x limit at window edges
 * - Less accurate than sliding window
 */
public class FixedWindowCounter implements RateLimiter {
    private final RateLimitConfig config;
    private final ConcurrentHashMap<String, WindowData> windows;
    
    public FixedWindowCounter(RateLimitConfig config) {
        this.config = config;
        this.windows = new ConcurrentHashMap<>();
    }
    
    @Override
    public boolean allowRequest(String key) {
        long now = System.currentTimeMillis();
        WindowData data = windows.computeIfAbsent(key, k -> new WindowData());
        
        synchronized (data) {
            long currentWindow = now / config.getWindowSizeMillis();
            
            // Reset counter if we're in a new window
            if (data.windowId.get() != currentWindow) {
                data.windowId.set(currentWindow);
                data.counter.set(0);
            }
            
            // Check if we're under the limit
            if (data.counter.get() < config.getMaxRequests()) {
                data.counter.incrementAndGet();
                return true;
            }
            
            return false;
        }
    }
    
    @Override
    public void reset(String key) {
        windows.remove(key);
    }
    
    private static class WindowData {
        final AtomicLong windowId = new AtomicLong(0);
        final AtomicInteger counter = new AtomicInteger(0);
    }
}
