package hu.galzol.jpmorgan.sales.product;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SalesMessageRecordTest {

    @Test
    public void testMessageReport() {
        SalesMessageDao msgDao = new SalesMessageDao();

        Integer id = msgDao.saveProduct("A", 1);
        assertThat(msgDao.getProduct(id).getId()).isEqualToComparingFieldByField(new SalesProduct(id, "A", 1));

        Integer id2 = msgDao.saveProduct("A", 1);
        assertThat(msgDao.getProduct(id).getId()).isEqualToComparingFieldByField(new SalesProduct(id2, "A", 1));
    }

    @Test
    public void testMessageCnt() {
        SalesMessageDao msgDao = new SalesMessageDao();

        msgDao.saveProduct("A", 1);
        msgDao.saveProduct("A", 1);
        assertThat(msgDao.getNumberOfProduct()).isEqualTo(2);
    }
}
