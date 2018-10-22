package hu.galzol.jpmorgan.sales.storage;

import hu.galzol.jpmorgan.sales.product.Operation;
import hu.galzol.jpmorgan.sales.product.ProductAdjustment;
import hu.galzol.jpmorgan.sales.product.SalesProduct;

import java.math.BigDecimal;
import java.util.List;

public interface SalesMessageStorage {
    SalesProduct saveProduct(String type, BigDecimal value, Integer quantity);

    void setProducts(List<SalesProduct> products);

    ProductAdjustment saveAdjustments(String type, BigDecimal value, Operation operation);

    List<SalesProduct> getProducts();

    List<ProductAdjustment> getAdjustments();

    Integer getNumberOfMessages();
}
