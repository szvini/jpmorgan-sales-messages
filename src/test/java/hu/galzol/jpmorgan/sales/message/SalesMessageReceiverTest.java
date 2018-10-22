package hu.galzol.jpmorgan.sales.message;

import hu.galzol.jpmorgan.sales.product.Operation;
import hu.galzol.jpmorgan.sales.product.SalesProduct;
import hu.galzol.jpmorgan.sales.storage.SalesMessageMemoryStorage;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class SalesMessageReceiverTest {

    @Test
    public void testReceiveProductPersistent() {
        SalesMessageReceiver r = getMessageReceiver(5, 5);

        r.receiveProduct("A", BigDecimal.ONE);
        assertThat(r.getStorage().getNumberOfMessages()).isEqualTo(1);
    }

    @Test
    public void testReportEvery10thProducts() {
        testReportProducts(30, 10, 3);
    }

    @Test
    public void testProductReportEdgeCases() {
        testReportProducts(9, 10, 0);
        testReportProducts(10, 10, 1);
        testReportProducts(11, 10, 1);
        testReportProducts(19, 10, 1);
        testReportProducts(20, 10, 2);
        testReportProducts(21, 10, 2);
    }

    @Test
    public void testAlwaysReportProducts() {
        testReportProducts(30, 0, 30);
    }

    private void testReportProducts(Integer messages, Integer reportFreq, Integer expectedReportCount) {
        SalesMessageMemoryStorage storage = new SalesMessageMemoryStorage();
        MockSalesMessageReporter report = new MockSalesMessageReporter();
        SalesMessageReceiver r = new SalesMessageReceiver(storage, report, reportFreq, 50);

        for (int i = 0; i < messages; i++) {
            r.receiveProduct("A", BigDecimal.ONE);
        }

        assertThat(report.getNumberOfProductReports()).isEqualTo(expectedReportCount);
        assertThat(storage.getNumberOfMessages()).isEqualTo(messages);
    }

    @Test
    public void testAdjustmentReportWhenReachedTheMaximum() {
        SalesMessageMemoryStorage storage = new SalesMessageMemoryStorage();
        MockSalesMessageReporter report = new MockSalesMessageReporter();
        SalesMessageReceiver receiver = new SalesMessageReceiver(storage, report, 1, 1);

        receiver.receiveProduct("A", BigDecimal.ONE);
        assertThat(report.getNumberOfAdjustmentReports()).isEqualTo(1);
        assertThat(receiver.getStorage().getNumberOfMessages()).isEqualTo(1);
        assertThat(receiver.hasReachedMaximumNumberOfMessages()).isTrue();
    }

    @Test
    public void testMaximumMessage() {
        int max = 3;
        SalesMessageMemoryStorage storage = new SalesMessageMemoryStorage();
        MockSalesMessageReporter report = new MockSalesMessageReporter();
        SalesMessageReceiver receiver = new SalesMessageReceiver(storage, report, 10, max);

        receiver.receiveProduct("A", BigDecimal.ONE);
        receiver.receiveProduct("A", BigDecimal.ONE, 1);
        receiver.receiveAdjustment("A", BigDecimal.ONE, Operation.MULTIPLY);

        assertThat(storage.getProducts()).hasSize(2);
        assertThat(storage.getAdjustments()).hasSize(1);
        assertThat(storage.getNumberOfMessages()).isEqualTo(3);
        assertThat(report.getNumberOfAdjustmentReports()).isEqualTo(1);
        assertThat(receiver.hasReachedMaximumNumberOfMessages()).isTrue();
    }

    @Test(expected = IllegalStateException.class)
    public void testPushMessageAfterReachedMax() {
        int max = 1;
        SalesMessageMemoryStorage storage = new SalesMessageMemoryStorage();
        MockSalesMessageReporter report = new MockSalesMessageReporter();
        SalesMessageReceiver receiver = new SalesMessageReceiver(storage, report, 10, max);

        receiver.receiveProduct("A", BigDecimal.ONE);
        try {
            receiver.receiveAdjustment("A", BigDecimal.ONE, Operation.MULTIPLY);
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("No more messages allowed!");
            assertThat(receiver.hasReachedMaximumNumberOfMessages()).isTrue();
            assertThat(storage.getProducts()).hasSize(1);
            assertThat(storage.getAdjustments()).hasSize(0);
            assertThat(storage.getNumberOfMessages()).isEqualTo(1);
            assertThat(report.getNumberOfAdjustmentReports()).isEqualTo(1);
            throw e;
        }

        fail("Last message should fail.");
    }

    @Test
    public void testNoMaximumMessage() {
        SalesMessageReceiver r = getMessageReceiver(0, 0);

        for (int i = 0; i < 5; i++) {
            r.receiveProduct("A", BigDecimal.ONE);
        }

        assertThat(r.getStorage().getNumberOfMessages()).isEqualTo(5);
    }

    @Test
    public void testProductAdjustment() {
        SalesMessageReceiver r = getMessageReceiver(0, 0);

        r.receiveProduct("A", BigDecimal.TEN);
        r.receiveProduct("A", BigDecimal.ONE, 2);
        r.receiveProduct("B", BigDecimal.TEN);
        r.receiveAdjustment("A", BigDecimal.ONE, Operation.ADD);
        r.receiveProduct("A", BigDecimal.TEN);
        r.receiveAdjustment("A", BigDecimal.ONE, Operation.ADD);

        assertThat(r.getStorage().getProducts()).containsExactly(
           new SalesProduct("A", new BigDecimal(12), 1),
           new SalesProduct("A", new BigDecimal(3), 2),
           new SalesProduct("B", new BigDecimal(10), 1),
           new SalesProduct("A", new BigDecimal(11), 1)
        );
    }

    private SalesMessageReceiver getMessageReceiver(Integer reportFreq, Integer maxMsg) {
        SalesMessageMemoryStorage storage = new SalesMessageMemoryStorage();
        MockSalesMessageReporter report = new MockSalesMessageReporter();
        return new SalesMessageReceiver(storage, report, reportFreq, maxMsg);
    }
}
