/**
 * Exception thrown when rate limit is exceeded.
 */
public class RateLimitExceededException extends Exception {
    public RateLimitExceededException(String message) {
        super(message);
    }
}
