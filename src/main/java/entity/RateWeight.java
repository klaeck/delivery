package entity;

import java.math.BigDecimal;

public class RateWeight {
    private final int id;
    private final double maxWeight;
    private final BigDecimal price;

    public RateWeight(int id, double maxWeight, BigDecimal price){
        this.id = id;
        this.maxWeight = maxWeight;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
