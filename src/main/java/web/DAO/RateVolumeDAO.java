package web.DAO;

import entity.Bid;
import entity.RateDistance;
import entity.RateVolume;
import exceptions.ErrorPageException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO: add log4j logging to this class
 */

public class RateVolumeDAO {
    private static final RateVolumeDAO instance = new RateVolumeDAO();

    private RateVolumeDAO() {
    }

    public static RateVolumeDAO getInstance() {
        return instance;
    }

    /**
     * @param id of the box
     * @return volume bean
     * @throws ErrorPageException to forward user into error page
     */
    public RateVolume getVolume(int id) throws ErrorPageException {
        String query = "SELECT * FROM rate_volume WHERE id=?";
        RateVolume volume = null;

        Connection con = ConnectionPull.getConnection();
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setDouble(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    volume = generateRateVolume(rs);

                } else {
                    System.err.printf("No SQL response in RateVolumeDAO.getPrice() method with parameters " +
                            "id: %d", id);
                    throw new ErrorPageException();
                }
            }

        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }

        return volume;
    }

    /**
     * DB getter for all volume rates
     * @return list of all volume rates
     */
    public List<RateVolume> getAllVolumeRates() {
        String queue = "SELECT * from rate_volume";
        List<RateVolume> volumes = new LinkedList<>();

        Connection con = ConnectionPull.getConnection();
        try (PreparedStatement ps = con.prepareStatement(queue)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    volumes.add(generateRateVolume(rs));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return volumes;

    }

    /**
     * Rate mapping method
     * @param rs is a ResultSet of SQL query
     * @return volume rate from the DB row
     * @throws SQLException in case of any SQL exception
     */
    private RateVolume generateRateVolume(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        double height = rs.getDouble("height");
        double length = rs.getDouble("length");
        double width = rs.getDouble("width");
        BigDecimal price = rs.getBigDecimal("price");

        return new RateVolume(id, height, length, width, price);
    }
}
