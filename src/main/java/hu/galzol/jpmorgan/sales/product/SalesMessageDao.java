package hu.galzol.jpmorgan.sales.product;

import java.util.HashMap;
import java.util.Map;

public class SalesMessageDao {

    private Integer idCnt = 0;
    private Map<Integer, SalesProduct> storage = new HashMap<>();

    public Integer saveProduct(String type, Integer value) {
        SalesProduct p = new SalesProduct(++idCnt, type, value);
        storage.put(p.getId(), p);
        return p.getId();
    }

    public SalesProduct getProduct(Integer id) {
        return storage.get(id);
    }

    public Integer getNumberOfProduct() {
        return storage.size();
    }
}
