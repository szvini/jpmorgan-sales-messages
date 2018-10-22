package hu.galzol.jpmorgan.sales.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public class ProductSummary {

    public static Map<String, SalesProduct> sumProducts(List<SalesProduct> products) {
        return products.stream()
                .collect(groupingBy(SalesProduct::getType))
                .entrySet()
                .stream()
                .collect(toMap(
                        Map.Entry::getKey,
                        e -> sumProducts(e.getKey(), e.getValue())
                ));
    }

    private static SalesProduct sumProducts(String type, List<SalesProduct> products) {
        BigDecimal sum = products.stream().map(p -> p.getValue().multiply(new BigDecimal(p.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        Integer qty = products.stream().mapToInt(SalesProduct::getQuantity).sum();
        return new SalesProduct(type, sum, qty);
    }


    public static List<SalesProduct> adjustProductsByType(ProductAdjustment adjustment, List<SalesProduct> products) {
        Map<Boolean, List<SalesProduct>> productMap = products.stream()
                .collect(partitioningBy(p -> p.getType().equals(adjustment.getType())));

        List<SalesProduct> newProducts = new ArrayList<>(productMap.get(false));
        newProducts.addAll(adjustProducts(adjustment, productMap.get(true)));
        return newProducts;
    }

    private static List<SalesProduct> adjustProducts(ProductAdjustment adjustment, List<SalesProduct> products) {
        return products.stream().map(adjustment::adjust).collect(toList());
    }
}
