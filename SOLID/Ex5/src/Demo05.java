public class Demo05 {
    public static void main(String[] args) {
        System.out.println("=== Export Demo ===");

        ExportRequest req = new ExportRequest("Weekly Report", SampleData.longBody());
        Exporter pdf = new PdfExporter();
        Exporter csv = new CsvExporter();
        Exporter json = new JsonExporter();

        System.out.println("PDF: "  + format(pdf,  req));
        System.out.println("CSV: "  + format(csv,  req));
        System.out.println("JSON: " + format(json, req));
    }

    /**
     * Calls export() and formats the result.  No try-catch needed: all
     * exporters honour the no-throw contract from {@link Exporter}.
     */
    private static String format(Exporter e, ExportRequest r) {
        ExportResult out = e.export(r);
        if (out.isError()) {
            return "ERROR: " + out.error;
        }
        return "OK bytes=" + out.bytes.length;
    }
}
