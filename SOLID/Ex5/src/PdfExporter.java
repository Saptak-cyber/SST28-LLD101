import java.nio.charset.StandardCharsets;

public class PdfExporter extends Exporter {
    private static final int MAX_BODY_CHARS = 20;

    /**
     * Returns an error result (never throws) when content exceeds the PDF
     * size limit, honoring the base contract's no-throw postcondition.
     */
    @Override
    public ExportResult export(ExportRequest req) {
        if (req.body != null && req.body.length() > MAX_BODY_CHARS) {
            return ExportResult.error("PDF cannot handle content > 20 chars");
        }
        String fakePdf = "PDF(" + req.title + "):" + req.body;
        return new ExportResult("application/pdf", fakePdf.getBytes(StandardCharsets.UTF_8));
    }
}
