package web.DAO;

import entity.RateDistance;
import exceptions.ErrorPageException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO: add log4j logging to this class
 */

public class RateDistanceDAO {
    private static final RateDistanceDAO instance = new RateDistanceDAO();

    private RateDistanceDAO(){}

    public static RateDistanceDAO getInstance(){
        return instance;
    }

    /**
     * @param distance - distance between two directions in km
     * @return rate for this param
     * @throws ErrorPageException to show user an error page
     */
    public BigDecimal getPrice(double distance) throws ErrorPageException {
        String query = "SELECT price FROM rate_distance WHERE max_distance >= ? LIMIT 1";
        BigDecimal price = new BigDecimal(-1);
        Connection con = ConnectionPull.getConnection();
        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setDouble(1, distance);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    price = rs.getBigDecimal("price");

                } else {
                    System.err.printf("No SQL response in RateDistanceDAO.getPrice() " +
                            "method with parameters distance: %f", distance);
                    throw new ErrorPageException();
                }
            }

        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }

        return price;
    }

    /**
     * DB getter for all distance rates
     * @return list of all distance rates
     */
    public List<RateDistance> getAllDistanceRates(){
        String queue = "SELECT * from rate_distance";
        List<RateDistance> distances = new LinkedList<>();

        Connection con = ConnectionPull.getConnection();
        try (PreparedStatement ps = con.prepareStatement(queue)){

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    distances.add(generateRateDistance(rs));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return distances;
    }

    /**
     * Rate mapping method
     * @param rs is a ResultSet of SQL query
     * @return rate from the row
     * @throws SQLException in case of any SQL exception
     */
    private RateDistance generateRateDistance(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        double maxDistance = rs.getDouble("max_distance");
        BigDecimal price = rs.getBigDecimal("price");

        return new RateDistance(id, maxDistance, price);
    }
}
