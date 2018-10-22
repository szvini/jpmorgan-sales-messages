package hu.galzol.jpmorgan.sales.storage;

import hu.galzol.jpmorgan.sales.product.Operation;
import hu.galzol.jpmorgan.sales.product.Product;
import hu.galzol.jpmorgan.sales.product.ProductAdjustment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SalesMessageMemoryStorage implements SalesMessageStorage {

    private List<Product> products = new ArrayList<>();
    private List<ProductAdjustment> adjustments = new ArrayList<>();

    @Override
    public Product saveProduct(String type, BigDecimal value, Integer quantity) {
        Product p = new Product(type, value, quantity);
        products.add(p);
        return p;
    }

    @Override
    public void setProducts(List<Product> products) {
        this.products.clear();
        this.products.addAll(products);
    }

    @Override
    public ProductAdjustment saveAdjustments(String type, BigDecimal value, Operation operation) {
        ProductAdjustment a = new ProductAdjustment(type, value, operation);
        adjustments.add(a);
        return a;
    }

    @Override
    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public List<ProductAdjustment> getAdjustments() {
        return Collections.unmodifiableList(adjustments);
    }

    @Override
    public Integer getNumberOfMessages() {
        return products.size() + adjustments.size();
    }

}
