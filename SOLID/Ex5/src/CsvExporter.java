import java.nio.charset.StandardCharsets;

public class CsvExporter extends Exporter {
    /**
     * Exports the request as plain text: title on the first line followed by
     * the body verbatim.  No data is altered or discarded.
     */
    @Override
    public ExportResult export(ExportRequest req) {
        String title = req.title == null ? "" : req.title;
        String body  = req.body  == null ? "" : req.body;
        String csv = title + "\n" + body;
        return new ExportResult("text/csv", csv.getBytes(StandardCharsets.UTF_8));
    }
}
