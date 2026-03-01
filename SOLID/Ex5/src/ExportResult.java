public class ExportResult {
    public final String contentType;
    public final byte[] bytes;
    /** Non-null when this result represents a failure; null means success. */
    public final String error;

    /** Success result. */
    public ExportResult(String contentType, byte[] bytes) {
        this.contentType = contentType;
        this.bytes = bytes;
        this.error = null;
    }

    /** Error result factory — use instead of throwing from export(). */
    public static ExportResult error(String message) {
        return new ExportResult(message);
    }

    /** Private constructor for error results. */
    private ExportResult(String errorMessage) {
        this.contentType = null;
        this.bytes = new byte[0];
        this.error = errorMessage;
    }

    public boolean isError() {
        return error != null;
    }
}
