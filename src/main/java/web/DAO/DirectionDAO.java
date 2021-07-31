package web.DAO;

import entity.Direction;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DirectionDAO {
    private static final DirectionDAO instance = new DirectionDAO();

    private DirectionDAO() {
    }

    public static DirectionDAO getInstance() {
        return instance;
    }

    /**
     * DB direction getter using id
     * @param id is an id of direction
     * @return Direction object
     */
    public Direction getDirection(int id) {
        String queue = "SELECT * FROM direction WHERE id=?";

        Connection con = ConnectionPull.getConnection();
        try (PreparedStatement ps = con.prepareStatement(queue)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return generateDirection(rs);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return null;
    }

    /**
     * DB getter for all directions
     * @return list of all directions
     */
    public List<Direction> getAllDirections() {
        String queue = "SELECT * FROM direction";
        List<Direction> directions = new LinkedList<>();

        Connection con = ConnectionPull.getConnection();
        try (PreparedStatement ps = con.prepareStatement(queue)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    directions.add(generateDirection(rs));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return directions;
    }

    /**
     * Direction mapping method
     * @param rs is a ResultSet of SQL query
     * @return Direction object from the row
     * @throws SQLException in case of any SQL exception
     */
    private Direction generateDirection(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String city = rs.getString("city");
        double x = rs.getDouble("x");
        double y = rs.getDouble("y");
        return new Direction(id, city, x, y);
    }
}

