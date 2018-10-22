package hu.galzol.jpmorgan.sales.product;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class ProductCalculations {

    public static List<SalesProduct> sumProducts(List<SalesProduct> products) {
        return products.stream()
                .collect(groupingBy(SalesProduct::getType))
                .entrySet()
                .stream()
                .map(e -> sumProducts(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(SalesProduct::getType))
                .collect(toList());
    }

    private static SalesProduct sumProducts(String type, List<SalesProduct> products) {
        BigDecimal sum = products.stream().map(p -> p.getValue().multiply(new BigDecimal(p.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        Integer qty = products.stream().mapToInt(SalesProduct::getQuantity).sum();
        return new SalesProduct(type, sum, qty);
    }


    public static List<SalesProduct> adjustProducts(ProductAdjustment adjustment, List<SalesProduct> products) {
        return products.stream()
                .map(p -> (p.getType().equals(adjustment.getType())) ? adjustment.adjust(p) : p)
                .collect(toList());
    }

}
