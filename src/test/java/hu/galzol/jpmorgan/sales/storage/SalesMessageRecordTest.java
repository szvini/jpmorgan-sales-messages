package hu.galzol.jpmorgan.sales.storage;

import hu.galzol.jpmorgan.sales.product.Operation;
import hu.galzol.jpmorgan.sales.product.ProductAdjustment;
import hu.galzol.jpmorgan.sales.product.SalesProduct;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class SalesMessageRecordTest {

    @Test
    public void testProductMessageRecord() {
        SalesMessageMemoryStorage storage = new SalesMessageMemoryStorage();

        SalesProduct a = storage.saveProduct("A", BigDecimal.ONE, 1);
        SalesProduct b = storage.saveProduct("B", BigDecimal.ONE, 1);
        assertThat(storage.getProducts()).containsOnly(a, b);
    }

    @Test
    public void testAdjustmentMessageRecord() {
        SalesMessageMemoryStorage storage = new SalesMessageMemoryStorage();

        ProductAdjustment aAdj = storage.saveAdjustments("A", BigDecimal.ONE, Operation.MULTIPLY);;
        ProductAdjustment bAdj = storage.saveAdjustments("B", BigDecimal.ONE, Operation.MULTIPLY);;
        assertThat(storage.getAdjustments()).containsOnly(aAdj, bAdj);
    }

    @Test
    public void testMessageCount() {
        SalesMessageMemoryStorage  storage = new SalesMessageMemoryStorage();

        storage.saveProduct("A", BigDecimal.ONE, 1);
        storage.saveProduct("A", BigDecimal.ONE, 1);
        storage.saveAdjustments("A", BigDecimal.ONE, Operation.MULTIPLY);
        assertThat(storage.getNumberOfMessages()).isEqualTo(3);
    }
}
