package hu.galzol.jpmorgan.sales.storage;

import hu.galzol.jpmorgan.sales.product.SalesProduct;

import java.math.BigDecimal;

public interface SalesMessageStorage {
    Integer saveProduct(String type, BigDecimal value);

    SalesProduct getProduct(Integer id);

    Integer getNumberOfProduct();
}
