package hu.galzol.jpmorgan.sales.product;

import java.math.BigDecimal;

public class ProductAdjustment {

    private final String type;
    private final BigDecimal value;
    private final Operation operation;

    public ProductAdjustment(String type, BigDecimal value, Operation operation) {
        this.type = type;
        this.value = value;
        this.operation = operation;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Operation getOperation() {
        return operation;
    }

    public SalesProduct adjust(SalesProduct p) {
        return new SalesProduct(p.getType(), operation.calc(p.getValue(), value) , p.getQuantity());
    }
}
