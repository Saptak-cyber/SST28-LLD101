/**
 * Base contract for all exporters (LSP-compliant).
 *
 * <p>Preconditions (callers must satisfy):
 * <ul>
 *   <li>{@code req} must be non-null.</li>
 * </ul>
 *
 * <p>Postconditions (implementations must guarantee):
 * <ul>
 *   <li>Never throws for any non-null {@code req}.</li>
 *   <li>Always returns a non-null {@link ExportResult}.</li>
 *   <li>If the format cannot represent the request, returns
 *       {@link ExportResult#error(String)} — never throws.</li>
 * </ul>
 *
 * <p>Subtypes MUST NOT tighten these preconditions.
 */
public abstract class Exporter {
    public abstract ExportResult export(ExportRequest req);
}
