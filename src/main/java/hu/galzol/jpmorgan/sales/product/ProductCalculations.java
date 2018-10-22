package hu.galzol.jpmorgan.sales.product;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class ProductCalculations {

    public static List<Product> sumProducts(List<Product> products) {
        return products.stream()
                .collect(groupingBy(Product::getType))
                .entrySet()
                .stream()
                .map(e -> sumProducts(e.getKey(), e.getValue()))
                .sorted(Comparator.comparing(Product::getType))
                .collect(toList());
    }

    private static Product sumProducts(String type, List<Product> products) {
        BigDecimal sum = products.stream().map(p -> p.getValue().multiply(new BigDecimal(p.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        Integer qty = products.stream().mapToInt(Product::getQuantity).sum();
        return new Product(type, sum, qty);
    }


    public static List<Product> adjustProducts(ProductAdjustment adjustment, List<Product> products) {
        return products.stream()
                .map(p -> (p.getType().equals(adjustment.getType())) ? adjustment.adjust(p) : p)
                .collect(toList());
    }

}
