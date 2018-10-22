package hu.galzol.jpmorgan.sales.message;

import hu.galzol.jpmorgan.sales.product.Operation;
import hu.galzol.jpmorgan.sales.product.ProductAdjustment;
import hu.galzol.jpmorgan.sales.product.ProductCalculations;
import hu.galzol.jpmorgan.sales.product.Product;
import hu.galzol.jpmorgan.sales.storage.SalesMessageMemoryStorage;

import java.math.BigDecimal;
import java.util.List;

public class SalesMessageReceiver {

    private final SalesMessageMemoryStorage salesMessageStorage;
    private final SalesMessageReporter salesMessageReporter;
    private final Integer reportFrequency;
    private final Integer maximumMessage;

    public SalesMessageReceiver(SalesMessageMemoryStorage SalesMessageStorage, SalesMessageReporter salesMessageReporter, Integer reportFrequency, Integer maximumMessage) {
        this.salesMessageStorage = SalesMessageStorage;
        this.salesMessageReporter = salesMessageReporter;
        this.reportFrequency = reportFrequency;
        this.maximumMessage = maximumMessage;
    }

    public void receiveProduct(String type, BigDecimal value) {
        receiveProduct(type, value, 1);
    }

    public void receiveProduct(String type, BigDecimal value, Integer quantity) {
        receiveMessage(() -> salesMessageStorage.saveProduct(type, value, quantity));
    }

    public void receiveAdjustment(String type, BigDecimal value, Operation operation) {
        receiveMessage(() -> {
            ProductAdjustment adj = salesMessageStorage.saveAdjustments(type, value, operation);
            List<Product> adjustedProducts = ProductCalculations.adjustProducts(adj, salesMessageStorage.getProducts());
            salesMessageStorage.setProducts(adjustedProducts);
        });
    }

    private void receiveMessage(MessageProcess messageProcess) {
        if (hasReachedMaximumNumberOfMessages()) {
            throw new IllegalStateException("No more messages allowed!");
        }
        messageProcess.process();
        if (hasHitReportFrequency()) {
            salesMessageReporter.reportProducts();
        }
        if (hasReachedMaximumNumberOfMessages()) {
            salesMessageReporter.reportAppliedAdjustments();
        }
    }

    /**
     * Check if number of messages has reached the report threshold.
     *
     * @return Return true if report frequency is less than one, in this case a report should be taken after each message.
     *         Else return true each time when number of messages hits the frequency.
     */
    public boolean hasHitReportFrequency() {
        return reportFrequency < 1 || salesMessageStorage.getNumberOfMessages() % reportFrequency == 0;
    }

    /**
     * Check if number of messages has reached the maximum number.
     *
     * @return Return false if maximum message is less than one, in this case any number of messages can be received.
     *         Else return true once number of messages reaches the upper limit.
     * @return
     */
    public boolean hasReachedMaximumNumberOfMessages() {
        return maximumMessage > 0 && salesMessageStorage.getNumberOfMessages() >= maximumMessage;
    }

    public SalesMessageMemoryStorage getStorage() {
        return salesMessageStorage;
    }

    public SalesMessageReporter getReporter() {
        return salesMessageReporter;
    }

    private interface MessageProcess {
        void process();
    }
}
