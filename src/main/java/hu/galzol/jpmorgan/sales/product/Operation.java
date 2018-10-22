package hu.galzol.jpmorgan.sales.product;

import java.math.BigDecimal;

public enum Operation {
    ADD {
        @Override
        public BigDecimal calc(BigDecimal a, BigDecimal b) {
            return a.add(b);
        }
    },
    SUBTRACT {
        @Override
        public BigDecimal calc(BigDecimal a, BigDecimal b) {
            return a.subtract(b);
        }
    },
    MULTIPLY {
        @Override
        public BigDecimal calc(BigDecimal a, BigDecimal b) {
            return a.multiply(b);
        }
    };

    public abstract BigDecimal calc(BigDecimal a, BigDecimal b);
}
