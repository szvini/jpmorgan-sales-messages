package hu.galzol.jpmorgan.sales.message;

import hu.galzol.jpmorgan.sales.product.SalesMessageDao;

public class SalesMessageReceiver {

    private final SalesMessageDao salesMessageDao;
    private final SalesMessageReporter salesMessageReporter;
    private final Integer reportFrequency;
    private final Integer maximumMessage;

    public SalesMessageReceiver(SalesMessageDao salesMessageDao, SalesMessageReporter salesMessageReporter, Integer reportFrequency, Integer maximumMessage) {
        this.salesMessageDao = salesMessageDao;
        this.salesMessageReporter = salesMessageReporter;
        this.reportFrequency = reportFrequency;
        this.maximumMessage = maximumMessage;
    }

    public void receiveProduct(String type, int value) {
        if (hasReachedMaximumNumberOfMessages()) {
            throw new IllegalStateException("No more messages allowed!");
        }
        salesMessageDao.saveProduct(type, value);
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
        return reportFrequency < 1 || salesMessageDao.getNumberOfProduct() % reportFrequency == 0;
    }

    /**
     * Check if number of messages has reached the maximum number.
     *
     * @return Return false if maximum message is less than one, in this case any number of messages can be received.
     *         Else return true once number of messages reaches the upper limit.
     * @return
     */
    public boolean hasReachedMaximumNumberOfMessages() {
        return maximumMessage > 0 && salesMessageDao.getNumberOfProduct() >= maximumMessage;
    }

    public SalesMessageDao getSalesMessageDao() {
        return salesMessageDao;
    }

    public SalesMessageReporter getSalesMessageReporter() {
        return salesMessageReporter;
    }
}
