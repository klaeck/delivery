package entity;

import java.math.BigDecimal;

public class RateDistance {
    private final int id;
    private final double maxDistance;
    private final BigDecimal price;

    public RateDistance(int id, double maxDistance, BigDecimal price){
        this.id = id;
        this.maxDistance = maxDistance;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
