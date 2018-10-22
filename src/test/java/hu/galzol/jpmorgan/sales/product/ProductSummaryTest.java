package hu.galzol.jpmorgan.sales.product;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductSummaryTest {

    @Test
    public void testSumOnUniqueProduct() {
        List<SalesProduct> sum = ProductSummary.sumProducts(Arrays.asList(
                new SalesProduct("apple", new BigDecimal(10), 1),
                new SalesProduct("banana", new BigDecimal(20), 2),
                new SalesProduct("cherry", new BigDecimal(30), 3)
        ));

        assertThat(sum).hasSize(3).containsExactly(
                new SalesProduct("apple", new BigDecimal(10), 1),
                new SalesProduct("banana", new BigDecimal(40), 2),
                new SalesProduct("cherry", new BigDecimal(90), 3)
        );
    }

    @Test
    public void testSumOnMultipleProducts() {
        List<SalesProduct> sum = ProductSummary.sumProducts(Arrays.asList(
                new SalesProduct("apple", new BigDecimal(10), 1),
                new SalesProduct("banana", new BigDecimal(20), 2),
                new SalesProduct("cherry", new BigDecimal(30), 3),
                new SalesProduct("apple", new BigDecimal(1), 1),
                new SalesProduct("banana", new BigDecimal(2), 2),
                new SalesProduct("cherry", new BigDecimal(3), 3)
        ));

        assertThat(sum).hasSize(3).containsExactly(
                new SalesProduct("apple", new BigDecimal(11), 2),
                new SalesProduct("banana", new BigDecimal(44), 4),
                new SalesProduct("cherry", new BigDecimal(99), 6)
        );
    }


    @Test
    public void testAdjustOnUniqueProduct() {
        List<SalesProduct> adj = ProductSummary.adjustProducts(
                new ProductAdjustment("apple", new BigDecimal(5), Operation.MULTIPLY),
                Arrays.asList(
                        new SalesProduct("apple", new BigDecimal(10), 1),
                        new SalesProduct("banana", new BigDecimal(20), 2),
                        new SalesProduct("cherry", new BigDecimal(30), 3)
                )
        );

        assertThat(adj).hasSize(3).containsExactly(
                new SalesProduct("apple", new BigDecimal(50), 1),
                new SalesProduct("banana", new BigDecimal(20), 2),
                new SalesProduct("cherry", new BigDecimal(30), 3)
        );
    }

    @Test
    public void testAdjustOnMultipleProducts() {
        List<SalesProduct> adj = ProductSummary.adjustProducts(
                new ProductAdjustment("apple", new BigDecimal(5), Operation.MULTIPLY),
                Arrays.asList(
                        new SalesProduct("apple", new BigDecimal(10), 1),
                        new SalesProduct("banana", new BigDecimal(20), 2),
                        new SalesProduct("cherry", new BigDecimal(30), 3),
                        new SalesProduct("apple", new BigDecimal(1), 1),
                        new SalesProduct("banana", new BigDecimal(2), 2),
                        new SalesProduct("cherry", new BigDecimal(3), 3)
                )
        );

        assertThat(adj).hasSize(6).containsExactly(
                new SalesProduct("apple", new BigDecimal(50), 1),
                new SalesProduct("banana", new BigDecimal(20), 2),
                new SalesProduct("cherry", new BigDecimal(30), 3),
                new SalesProduct("apple", new BigDecimal(5), 1),
                new SalesProduct("banana", new BigDecimal(2), 2),
                new SalesProduct("cherry", new BigDecimal(3), 3)
        );
    }

    @Test
    public void testMultipleAdjustOnMultipleProducts() {
        List<SalesProduct> products = Arrays.asList(
                new SalesProduct("apple", new BigDecimal(10), 1),
                new SalesProduct("banana", new BigDecimal(20), 2),
                new SalesProduct("cherry", new BigDecimal(30), 3),
                new SalesProduct("apple", new BigDecimal(1), 1),
                new SalesProduct("banana", new BigDecimal(2), 2),
                new SalesProduct("cherry", new BigDecimal(3), 3)
        );

        List<SalesProduct> adj = ProductSummary.adjustProducts(new ProductAdjustment("apple", new BigDecimal(5), Operation.ADD), products);
        List<SalesProduct> adj2 = ProductSummary.adjustProducts(new ProductAdjustment("banana", new BigDecimal(5), Operation.SUBTRACT), adj);
        List<SalesProduct> adj3 = ProductSummary.adjustProducts(new ProductAdjustment("cherry", new BigDecimal(5), Operation.MULTIPLY), adj2);

        assertThat(adj3).hasSize(6).containsExactly(
                new SalesProduct("apple", new BigDecimal(15), 1),
                new SalesProduct("banana", new BigDecimal(15), 2),
                new SalesProduct("cherry", new BigDecimal(150), 3),
                new SalesProduct("apple", new BigDecimal(6), 1),
                new SalesProduct("banana", new BigDecimal(-3), 2),
                new SalesProduct("cherry", new BigDecimal(15), 3)
        );
    }

}
