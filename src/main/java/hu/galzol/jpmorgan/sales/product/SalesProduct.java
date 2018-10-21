package hu.galzol.jpmorgan.sales.product;

public class SalesProduct {
    private final Integer id;
    private final String type;
    private final Integer value;

    public SalesProduct(Integer id, String type, Integer value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Integer getValue() {
        return value;
    }

}
