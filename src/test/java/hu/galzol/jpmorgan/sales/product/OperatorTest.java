package hu.galzol.jpmorgan.sales.product;

import org.junit.Test;

import java.math.BigDecimal;

import static hu.galzol.jpmorgan.sales.product.Operation.ADD;
import static hu.galzol.jpmorgan.sales.product.Operation.MULTIPLY;
import static hu.galzol.jpmorgan.sales.product.Operation.SUBTRACT;
import static org.assertj.core.api.Assertions.assertThat;

public class OperatorTest {

    private BigDecimal ten = new BigDecimal("10");
    private BigDecimal twenty = new BigDecimal("20");

    @Test
    public void add() {
        assertThat(ADD.calc(ten, twenty)).isEqualByComparingTo(new BigDecimal("30"));
    }

    @Test
    public void subtract() {
        assertThat(SUBTRACT.calc(ten, twenty)).isEqualByComparingTo(new BigDecimal("-10"));
    }

    @Test
    public void multiply() {
        assertThat(MULTIPLY.calc(ten, twenty)).isEqualByComparingTo(new BigDecimal("200"));
    }

}
