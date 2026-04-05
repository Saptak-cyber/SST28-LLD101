/**
 * Gateway for calling external resources with rate limiting.
 * Demonstrates how to integrate rate limiting into business logic.
 */
public class ExternalResourceGateway {
    private final RateLimiter rateLimiter;
    
    public ExternalResourceGateway(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }
    
    /**
     * Calls external resource if rate limit allows.
     * 
     * @param key The rate limiting key (e.g., tenantId, customerId)
     * @param request The request to send to external resource
     * @return Response from external resource
     * @throws RateLimitExceededException if rate limit is exceeded
     */
    public <T, R> R callExternalResource(String key, T request, ExternalResourceClient<T, R> client) 
            throws RateLimitExceededException {
        
        // Check rate limit before making external call
        if (!rateLimiter.allowRequest(key)) {
            throw new RateLimitExceededException(
                "Rate limit exceeded for key: " + key
            );
        }
        
        // Make the actual external call
        return client.call(request);
    }
    
    /**
     * Functional interface for external resource clients.
     */
    @FunctionalInterface
    public interface ExternalResourceClient<T, R> {
        R call(T request);
    }
}
