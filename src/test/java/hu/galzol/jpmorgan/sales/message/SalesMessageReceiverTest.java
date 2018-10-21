package hu.galzol.jpmorgan.sales.message;

import hu.galzol.jpmorgan.sales.product.SalesMessageDao;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class SalesMessageReceiverTest {

    @Test
    public void testReceiveProductPersistent() {
        SalesMessageReceiver r = getMessageReceiver(5, 5);

        r.receiveProduct("A", 1);
        assertThat(r.getSalesMessageDao().getNumberOfProduct()).isEqualTo(1);
    }

    @Test
    public void testReportEvery10thProducts() {
        testReport(30, 10, 3);
    }

    @Test
    public void testReportEdgeCases() {
        testReport(9, 10, 0);
        testReport(10, 10, 1);
        testReport(11, 10, 1);
        testReport(19, 10, 1);
        testReport(20, 10, 2);
        testReport(21, 10, 2);
    }

    @Test
    public void testAlwaysReportEvery10thProducts() {
        testReport(30, 0, 30);
    }

    private void testReport(Integer messages, Integer reportFreq, Integer expectedReportCount) {
        SalesMessageDao storage = new SalesMessageDao();
        MockSalesMessageReporter report = new MockSalesMessageReporter();
        SalesMessageReceiver r = new SalesMessageReceiver(storage, report, reportFreq, 50);

        for (int i = 0; i < messages; i++) {
            r.receiveProduct("A" + i, i);
        }

        assertThat(report.getNumberOfReports()).isEqualTo(expectedReportCount);
        assertThat(storage.getNumberOfProduct()).isEqualTo(messages);
    }

    @Test(expected = IllegalStateException.class)
    public void testMaximumReport() {
        int max = 4;
        SalesMessageReceiver r = getMessageReceiver(10, max);

        for (int i = 0; i < 5; i++) {
            try {
                r.receiveProduct("A", 1);
            } catch (IllegalStateException e) {
                assertThat(i).withFailMessage("Error should be thrown in the %sth iteration.", max).isEqualTo(max);
                assertThat(e.getMessage()).isEqualTo("No more messages allowed!");
                assertThat(r.getSalesMessageDao().getNumberOfProduct()).isEqualTo(4);
                throw e;
            }
        }

        fail("Last message should fail.");
    }

    @Test
    public void testNoMaximumReport() {
        SalesMessageReceiver r = getMessageReceiver(0, 0);

        for (int i = 0; i < 5; i++) {
            r.receiveProduct("A", 1);
        }

        assertThat(r.getSalesMessageDao().getNumberOfProduct()).isEqualTo(5);
    }


    private SalesMessageReceiver getMessageReceiver(Integer reportFreq, Integer maxMsg) {
        SalesMessageDao storage = new SalesMessageDao();
        MockSalesMessageReporter report = new MockSalesMessageReporter();
        return new SalesMessageReceiver(storage, report, reportFreq, maxMsg);
    }
}
