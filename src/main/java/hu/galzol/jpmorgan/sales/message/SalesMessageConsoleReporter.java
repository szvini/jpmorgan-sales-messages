package hu.galzol.jpmorgan.sales.message;

import hu.galzol.jpmorgan.sales.product.ProductCalculations;
import hu.galzol.jpmorgan.sales.storage.SalesMessageMemoryStorage;

public class SalesMessageConsoleReporter implements SalesMessageReporter {

    private final SalesMessageMemoryStorage storage;

    public SalesMessageConsoleReporter(SalesMessageMemoryStorage storage) {
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
