package models;

import java.math.BigDecimal;
import java.util.Date;

public class FlowerListed extends Flower{
    private BigDecimal price;

    public FlowerListed() {
    }

    public FlowerListed(int id, String name, String kind, Date date, String ownerEmail, String status, BigDecimal price) {
        super(id, name, kind, date, ownerEmail, status);
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
