package hu.galzol.jpmorgan.sales.storage;

import hu.galzol.jpmorgan.sales.product.SalesProduct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SalesMessageMemoryStorage implements SalesMessageStorage {

    private List<SalesProduct> storage = new ArrayList<>();

    @Override
    public Integer saveProduct(String type, BigDecimal value) {
        SalesProduct p = new SalesProduct(type, value, 10);
        storage.add(p);
        return storage.size() - 1;
    }

    @Override
    public SalesProduct getProduct(Integer id) {
        return storage.get(id);
    }

    @Override
    public Integer getNumberOfProduct() {
        return storage.size();
    }
}
