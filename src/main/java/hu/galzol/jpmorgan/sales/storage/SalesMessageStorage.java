package hu.galzol.jpmorgan.sales.storage;

import hu.galzol.jpmorgan.sales.product.Operation;
import hu.galzol.jpmorgan.sales.product.Product;
import hu.galzol.jpmorgan.sales.product.ProductAdjustment;

import java.math.BigDecimal;
import java.util.List;

public interface SalesMessageStorage {
    Product saveProduct(String type, BigDecimal value, Integer quantity);

    void setProducts(List<Product> products);

    ProductAdjustment saveAdjustments(String type, BigDecimal value, Operation operation);

    List<Product> getProducts();

    List<ProductAdjustment> getAdjustments();

    Integer getNumberOfMessages();
}
