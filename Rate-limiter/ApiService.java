/**
 * Example API service demonstrating the complete flow.
 * Shows how rate limiting is applied only when external calls are needed.
 */
public class ApiService {
    private final ExternalResourceGateway gateway;
    
    public ApiService(ExternalResourceGateway gateway) {
        this.gateway = gateway;
    }
    
    /**
     * Handles client API request.
     * Rate limiting is only applied if external call is needed.
     */
    public String handleClientRequest(String tenantId, ClientRequest request) {
        // Step 1: Business logic runs first
        boolean needsExternalCall = evaluateBusinessLogic(request);
        
        // Step 2: If no external call needed, no rate limiting check
        if (!needsExternalCall) {
            return "Processed locally without external call";
        }
        
        // Step 3: External call needed - check rate limit
        try {
            String result = gateway.callExternalResource(
                tenantId,
                request,
                this::callExternalApi
            );
            return "Success: " + result;
        } catch (RateLimitExceededException e) {
            // Step 4: Handle rate limit gracefully
            return handleRateLimitExceeded(tenantId, e);
        }
    }
    
    private boolean evaluateBusinessLogic(ClientRequest request) {
        // Business logic to determine if external call is needed
        return request.requiresExternalProcessing();
    }
    
    private String callExternalApi(ClientRequest request) {
        // Simulate external API call
        return "External API response for: " + request.getData();
    }
    
    private String handleRateLimitExceeded(String tenantId, RateLimitExceededException e) {
        // Handle gracefully: log, return error, queue for later, etc.
        System.err.println("Rate limit exceeded for tenant: " + tenantId);
        return "Error: Rate limit exceeded. Please try again later.";
    }
    
    // Simple request class for demonstration
    public static class ClientRequest {
        private final String data;
        private final boolean requiresExternal;
        
        public ClientRequest(String data, boolean requiresExternal) {
            this.data = data;
            this.requiresExternal = requiresExternal;
        }
        
        public String getData() {
            return data;
        }
        
        public boolean requiresExternalProcessing() {
            return requiresExternal;
        }
    }
}
