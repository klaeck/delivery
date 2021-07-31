package entity;

import java.math.BigDecimal;

public class RateVolume {
    private final int id;
    private final double height;
    private final double length;
    private final double width;
    private final BigDecimal price;

    public RateVolume(int id, double height, double length, double width, BigDecimal price){
        this.id = id;
        this.height = height;
        this.length = length;
        this.width = width;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public double getHeight() {
        return height;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }
}
