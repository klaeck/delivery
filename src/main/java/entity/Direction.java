package entity;

public class Direction {
    private final int id;
    private final String city;
    private final double x;
    private final double y;

    public Direction(int id, String city, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    /**
     *
     * @param d1 - department
     * @param d2 - destination
     * @return - algebraic distance between two points that was multiplied by 111 to convert degrees into km
     */
    public static double getDistance(Direction d1, Direction d2){
        return Math.sqrt(Math.pow((d2.x - d1.x), 2) + Math.pow((d2.y - d1.y), 2)) * 111;
    }

    @Override
    public String toString() {
        return "City: " + city +
                "\nx: " + x +
                "\ny: " + y + "\n";
    }
}
