import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Unit tests for rate limiter implementations.
 * Demonstrates thread-safety and correctness.
 */
public class RateLimiterTest {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Rate Limiter Tests ===\n");
        
        testFixedWindowBasic();
        testSlidingWindowBasic();
        testThreadSafety();
        testMultipleKeys();
        testReset();
        
        System.out.println("\n✓ All tests passed!");
    }
    
    private static void testFixedWindowBasic() {
        System.out.println("Test: Fixed Window Basic");
        RateLimitConfig config = RateLimitConfig.perMinute(3);
        RateLimiter limiter = new FixedWindowCounter(config);
        
        assert limiter.allowRequest("key1") == true;
        assert limiter.allowRequest("key1") == true;
        assert limiter.allowRequest("key1") == true;
        assert limiter.allowRequest("key1") == false; // 4th request denied
        
        System.out.println("  ✓ Passed\n");
    }
    
    private static void testSlidingWindowBasic() throws InterruptedException {
        System.out.println("Test: Sliding Window Basic");
        RateLimitConfig config = RateLimitConfig.perSecond(2);
        RateLimiter limiter = new SlidingWindowCounter(config);
        
        assert limiter.allowRequest("key1") == true;
        assert limiter.allowRequest("key1") == true;
        assert limiter.allowRequest("key1") == false; // 3rd request denied
        
        Thread.sleep(1100); // Wait for window to slide
        
        assert limiter.allowRequest("key1") == true; // Should work now
        
        System.out.println("  ✓ Passed\n");
    }
    
    private static void testThreadSafety() throws InterruptedException {
        System.out.println("Test: Thread Safety");
        RateLimitConfig config = RateLimitConfig.perMinute(100);
        RateLimiter limiter = new SlidingWindowCounter(config);
        
        int threadCount = 10;
        int requestsPerThread = 20;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger allowedCount = new AtomicInteger(0);
        
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                for (int j = 0; j < requestsPerThread; j++) {
                    if (limiter.allowRequest("key1")) {
                        allowedCount.incrementAndGet();
                    }
                }
                latch.countDown();
            });
        }
        
        latch.await();
        executor.shutdown();
        
        // Should allow exactly 100 requests
        assert allowedCount.get() == 100 : "Expected 100, got " + allowedCount.get();
        
        System.out.println("  ✓ Passed (allowed " + allowedCount.get() + "/200 requests)\n");
    }
    
    private static void testMultipleKeys() {
        System.out.println("Test: Multiple Keys");
        RateLimitConfig config = RateLimitConfig.perMinute(2);
        RateLimiter limiter = new FixedWindowCounter(config);
        
        // Different keys should have independent limits
        assert limiter.allowRequest("tenant1") == true;
        assert limiter.allowRequest("tenant1") == true;
        assert limiter.allowRequest("tenant1") == false;
        
        assert limiter.allowRequest("tenant2") == true; // Different key, should work
        assert limiter.allowRequest("tenant2") == true;
        assert limiter.allowRequest("tenant2") == false;
        
        System.out.println("  ✓ Passed\n");
    }
    
    private static void testReset() {
        System.out.println("Test: Reset");
        RateLimitConfig config = RateLimitConfig.perMinute(2);
        RateLimiter limiter = new FixedWindowCounter(config);
        
        assert limiter.allowRequest("key1") == true;
        assert limiter.allowRequest("key1") == true;
        assert limiter.allowRequest("key1") == false;
        
        limiter.reset("key1");
        
        assert limiter.allowRequest("key1") == true; // Should work after reset
        
        System.out.println("  ✓ Passed\n");
    }
}
