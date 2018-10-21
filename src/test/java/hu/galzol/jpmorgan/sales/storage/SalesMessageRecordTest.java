package hu.galzol.jpmorgan.sales.storage;

import hu.galzol.jpmorgan.sales.product.SalesProduct;
import hu.galzol.jpmorgan.sales.storage.SalesMessageMemoryStorage;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class SalesMessageRecordTest {

    @Test
    public void testMessageReport() {
        SalesMessageMemoryStorage storage = new SalesMessageMemoryStorage();

        Integer a = storage.saveProduct("A", BigDecimal.ONE, 1);
        assertThat(storage.getProduct(a)).isEqualTo(new SalesProduct("A", BigDecimal.ONE, 1));

        Integer b = storage.saveProduct("B", BigDecimal.ONE, 1);
        assertThat(storage.getProduct(b)).isEqualTo(new SalesProduct("B", BigDecimal.ONE, 1));
    }

    @Test
    public void testMessageCnt() {
        SalesMessageMemoryStorage msgDao = new SalesMessageMemoryStorage();

        msgDao.saveProduct("A", BigDecimal.ONE, 1);
        msgDao.saveProduct("A", BigDecimal.ONE, 1);
        assertThat(msgDao.getNumberOfProduct()).isEqualTo(2);
    }
}
