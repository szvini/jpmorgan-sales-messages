package hu.galzol.jpmorgan.sales.message;

import hu.galzol.jpmorgan.sales.storage.SalesMessageMemoryStorage;

import java.math.BigDecimal;

public class SalesMessageReceiver {

    private final SalesMessageMemoryStorage SalesMessageStorage;
    private final SalesMessageReporter salesMessageReporter;
    private final Integer reportFrequency;
    private final Integer maximumMessage;

    public SalesMessageReceiver(SalesMessageMemoryStorage SalesMessageStorage, SalesMessageReporter salesMessageReporter, Integer reportFrequency, Integer maximumMessage) {
        this.SalesMessageStorage = SalesMessageStorage;
        this.salesMessageReporter = salesMessageReporter;
        this.reportFrequency = reportFrequency;
        this.maximumMessage = maximumMessage;
    }

    public void receiveProduct(String type, BigDecimal value) {
        if (hasReachedMaximumNumberOfMessages()) {
            throw new IllegalStateException("No more messages allowed!");
        }
        SalesMessageStorage.saveProduct(type, value);
        if (hasHitReportFrequency()) {
            salesMessageReporter.reportProducts();
        }
    }

    /**
     * Check if number of messages has reached the report threshold.
     *
     * @return Return true if report frequency is less than one, in this case a report should be taken after each message.
     *         Else return true each time when number of messages hits the frequency.
     */
    public boolean hasHitReportFrequency() {
        return reportFrequency < 1 || SalesMessageStorage.getNumberOfProduct() % reportFrequency == 0;
    }

    /**
     * Check if number of messages has reached the maximum number.
     *
     * @return Return false if maximum message is less than one, in this case any number of messages can be received.
     *         Else return true once number of messages reaches the upper limit.
     * @return
     */
    public boolean hasReachedMaximumNumberOfMessages() {
        return maximumMessage > 0 && SalesMessageStorage.getNumberOfProduct() >= maximumMessage;
    }

    public SalesMessageMemoryStorage getStorage() {
        return SalesMessageStorage;
    }

    public SalesMessageReporter getReporter() {
        return salesMessageReporter;
    }
}
