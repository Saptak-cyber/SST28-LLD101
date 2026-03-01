import java.util.*;

public class CafeteriaSystem {
    private final Map<String, MenuItem> menu = new LinkedHashMap<>();
    private final InvoiceStore store;
    private final TaxRules taxRules;
    private final DiscountRules discountRules;
    private final InvoiceFormatter formatter;
    private int invoiceSeq = 1000;

    public CafeteriaSystem() {
        this(new FileStore(), new StandardTaxRules(), new StandardDiscountRules(), new StandardInvoiceFormatter());
    }

    public CafeteriaSystem(InvoiceStore store, TaxRules taxRules, DiscountRules discountRules, InvoiceFormatter formatter) {
        this.store = store;
        this.taxRules = taxRules;
        this.discountRules = discountRules;
        this.formatter = formatter;
    }

    public void addToMenu(MenuItem i) { 
        menu.put(i.id, i); 
    }

    public void checkout(String customerType, List<OrderLine> lines) {
        String invId = "INV-" + (++invoiceSeq);

        double subtotal = calculateSubtotal(lines);
        double taxPct = taxRules.calculateTaxPercent(customerType);
        double tax = subtotal * (taxPct / 100.0);
        double discount = discountRules.calculateDiscount(customerType, subtotal, lines.size());
        double total = subtotal + tax - discount;

        String printable = formatter.format(invId, lines, menu, subtotal, taxPct, tax, discount, total);
        System.out.print(printable);

        store.save(invId, printable);
        System.out.println("Saved invoice: " + invId + " (lines=" + store.countLines(invId) + ")");
    }

    private double calculateSubtotal(List<OrderLine> lines) {
        double subtotal = 0.0;
        for (OrderLine l : lines) {
            MenuItem item = menu.get(l.itemId);
            subtotal += item.price * l.qty;
        }
        return subtotal;
    }
}
