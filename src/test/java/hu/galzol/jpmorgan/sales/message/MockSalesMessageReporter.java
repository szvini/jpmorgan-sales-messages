package hu.galzol.jpmorgan.sales.message;

public class MockSalesMessageReporter implements SalesMessageReporter {

    private Integer call = 0;

    @Override
    public void reportProducts() {
        call++;
    }

    public Integer getNumberOfReports() {
        return call;
    }
}
