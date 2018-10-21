package hu.galzol.jpmorgan.sales.product;

import java.math.BigDecimal;
import java.util.Objects;

public class SalesProduct {
    private final String type;
    private final BigDecimal value;
    private final Integer quantity;

    public SalesProduct(String type, BigDecimal value, Integer quantity) {
        this.type = type;
        this.value = value;
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesProduct that = (SalesProduct) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(value, that.value) &&
                Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value, quantity);
    }
}
