import java.nio.charset.StandardCharsets;

public class JsonExporter extends Exporter {
    /**
     * Exports as JSON.  Null fields are serialized as empty strings so the
     * output shape is always consistent.  Per the base contract, req must be
     * non-null; a NullPointerException here is a caller violation, not a
     * subtype precondition tightening.
     */
    @Override
    public ExportResult export(ExportRequest req) {
        String json = "{\"title\":\"" + escape(req.title) + "\",\"body\":\"" + escape(req.body) + "\"}";
        return new ExportResult("application/json", json.getBytes(StandardCharsets.UTF_8));
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
