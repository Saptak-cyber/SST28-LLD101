/**
 * Demonstration of the rate limiting system.
 * Shows how to switch between algorithms without changing business logic.
 */
public class Demo {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Rate Limiting System Demo ===\n");
        
        // Demo 1: Fixed Window Counter
        System.out.println("--- Demo 1: Fixed Window Counter ---");
        demoFixedWindow();
        
        Thread.sleep(1000);
        
        // Demo 2: Sliding Window Counter
        System.out.println("\n--- Demo 2: Sliding Window Counter ---");
        demoSlidingWindow();
        
        Thread.sleep(1000);
        
        // Demo 3: Switching algorithms
        System.out.println("\n--- Demo 3: Algorithm Switching ---");
        demoAlgorithmSwitching();
        
        // Demo 4: Complete flow
        System.out.println("\n--- Demo 4: Complete API Flow ---");
        demoCompleteFlow();
    }
    
    private static void demoFixedWindow() {
        RateLimitConfig config = RateLimitConfig.perMinute(5);
        RateLimiter limiter = new FixedWindowCounter(config);
        
        String tenant = "T1";
        
        // Make 7 requests
        for (int i = 1; i <= 7; i++) {
            boolean allowed = limiter.allowRequest(tenant);
            System.out.println("Request " + i + ": " + (allowed ? "ALLOWED" : "DENIED"));
        }
    }
    
    private static void demoSlidingWindow() {
        RateLimitConfig config = RateLimitConfig.perSecond(3);
        RateLimiter limiter = new SlidingWindowCounter(config);
        
        String tenant = "T2";
        
        // Make requests with timing
        for (int i = 1; i <= 5; i++) {
            boolean allowed = limiter.allowRequest(tenant);
            System.out.println("Request " + i + " at " + System.currentTimeMillis() + ": " 
                + (allowed ? "ALLOWED" : "DENIED"));
            
            if (i == 3) {
                try {
                    Thread.sleep(1100); // Wait for window to slide
                    System.out.println("  [Waited 1.1 seconds]");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    private static void demoAlgorithmSwitching() {
        RateLimitConfig config = RateLimitConfig.perMinute(3);
        
        // Use Fixed Window
        System.out.println("Using Fixed Window:");
        RateLimiter limiter1 = RateLimiterFactory.create(
            RateLimiterFactory.Algorithm.FIXED_WINDOW, 
            config
        );
        testLimiter(limiter1, "customer123");
        
        // Switch to Sliding Window - no business logic changes!
        System.out.println("\nUsing Sliding Window:");
        RateLimiter limiter2 = RateLimiterFactory.create(
            RateLimiterFactory.Algorithm.SLIDING_WINDOW, 
            config
        );
        testLimiter(limiter2, "customer456");
    }
    
    private static void testLimiter(RateLimiter limiter, String key) {
        for (int i = 1; i <= 4; i++) {
            boolean allowed = limiter.allowRequest(key);
            System.out.println("  Request " + i + ": " + (allowed ? "✓" : "✗"));
        }
    }
    
    private static void demoCompleteFlow() {
        // Setup
        RateLimitConfig config = RateLimitConfig.perMinute(5);
        RateLimiter limiter = new SlidingWindowCounter(config);
        ExternalResourceGateway gateway = new ExternalResourceGateway(limiter);
        ApiService apiService = new ApiService(gateway);
        
        String tenantId = "tenant-xyz";
        
        // Request 1: No external call needed
        System.out.println("Request 1 (local processing):");
        ApiService.ClientRequest req1 = new ApiService.ClientRequest("data1", false);
        String result1 = apiService.handleClientRequest(tenantId, req1);
        System.out.println("  " + result1);
        
        // Requests 2-7: External calls needed
        System.out.println("\nRequests 2-7 (external calls):");
        for (int i = 2; i <= 7; i++) {
            ApiService.ClientRequest req = new ApiService.ClientRequest("data" + i, true);
            String result = apiService.handleClientRequest(tenantId, req);
            System.out.println("  Request " + i + ": " + result);
        }
    }
}
