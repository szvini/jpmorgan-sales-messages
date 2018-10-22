package hu.galzol.jpmorgan.sales.message;

public class MockSalesMessageReporter implements SalesMessageReporter {

    private Integer callReportProducts = 0;
    private Integer callReportAdjustments = 0;

    @Override
    public void reportProducts() {
        callReportProducts++;
    }

    @Override
    public void reportAppliedAdjustments() {
        callReportAdjustments++;
    }

    public Integer getNumberOfProductReports() {
        return callReportProducts;
    }

    public Integer getNumberOfAdjustmentReports() {
        return callReportAdjustments;
    }
}
