package entity;

import enums.Status;
import enums.Type;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class Bid {
    private final int id;
    private final int userID;
    private Status status;
    private final Direction fromDirection;
    private final Direction toDirection;
    private final Type type;
    private final double weight;
    private final String volume;
    private final Date deliveryDate;
    private final Date createdDate;
    private final BigDecimal totalPrice;

    public Bid(int id, int userID, Direction fromDirection, Direction toDirection, Status status,
               Type type, double weight, String volume, Date deliveryDate, Date createdDate, BigDecimal totalPrice) {
        this.id = id;
        this.userID = userID;
        this.fromDirection = fromDirection;
        this.toDirection = toDirection;
        this.type = type;
        this.weight = weight;
        this.volume = volume;
        this.status = status;
        this.deliveryDate = deliveryDate;
        this.createdDate = createdDate;
        this.totalPrice = totalPrice;
    }

    public int getUserID() {
        return userID;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Direction getFromDirection() {
        return fromDirection;
    }

    public Direction getToDirection() {
        return toDirection;
    }

    public double getWeight() {
        return weight;
    }

    public String getVolume() {
        return volume;
    }

    public Type getType() {
        return type;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public int getId() {
        return id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
