package ee.ttu.a103944.shopandr.model;

import java.math.BigDecimal;


public class ItemProps {
    private BigDecimal price = new BigDecimal(0);
    private int quantity = 0;
    private BigDecimal total = new BigDecimal(0);

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
