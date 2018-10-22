package hu.galzol.jpmorgan.sales.message;

import hu.galzol.jpmorgan.sales.product.ProductCalculations;
import hu.galzol.jpmorgan.sales.storage.SalesMessageStorage;

public class SalesMessageConsoleReporter implements SalesMessageReporter {

    private final SalesMessageStorage storage;

    public SalesMessageConsoleReporter(SalesMessageStorage storage) {
        this.storage = storage;
    }

    @Override
    public void reportProducts() {
        System.out.println("\nProduct sales report:");
        ProductCalculations.sumProducts(storage.getProducts()).forEach(System.out::println);
    }

    @Override
    public void reportAppliedAdjustments() {
        System.out.println("\nProduct adjustments:");
        storage.getAdjustments().forEach(System.out::println);
    }

}
