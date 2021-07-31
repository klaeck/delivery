package web.DAO;

import entity.RateDistance;
import entity.RateWeight;
import exceptions.ErrorPageException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO: add log4j logging to this class
 */

public class RateWeightDAO {
    private static final RateWeightDAO instance = new RateWeightDAO();

    private RateWeightDAO(){ }

    public static RateWeightDAO getInstance(){
        return instance;
    }

    /**
     * @param weight - weight of the package in kg
     * @return price for this package weight
     * @throws ErrorPageException to show user an error page
     */
    public BigDecimal getPrice(double weight) throws ErrorPageException {
        String query = "SELECT price FROM rate_weight WHERE max_weight >= ? LIMIT 1";
        BigDecimal price = new BigDecimal(-1);

        Connection con = ConnectionPull.getConnection();
        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setDouble(1, weight);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    price = rs.getBigDecimal("price");

                } else {
                    System.err.printf("No SQL response in RateWeightDAO.getPrice() " +
                            "method with parameters weight: %f", weight);
                    throw new ErrorPageException();
                }
            }

        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }

        return price;
    }

    /**
     * DB getter for all weight rates
     * @return list of rates
     */
    public List<RateWeight> getAllWeightRates(){
        String queue = "SELECT * from rate_weight";
        List<RateWeight> weights = new LinkedList<>();

        Connection con = ConnectionPull.getConnection();
        try (PreparedStatement ps = con.prepareStatement(queue)){

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    weights.add(generateRateWeight(rs));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return weights;
    }

    /**
     * Rate mapping method
     * @param rs is a ResultSet of SQL query
     * @return rate from the row
     * @throws SQLException in case of any SQL exception
     */
    private RateWeight generateRateWeight(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        double maxWeight = rs.getDouble("max_weight");
        BigDecimal price = rs.getBigDecimal("price");

        return new RateWeight(id, maxWeight, price);
    }

}
