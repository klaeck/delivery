package web.DAO;

import entity.Bid;
import entity.Direction;
import enums.Status;
import enums.Type;
import exceptions.ErrorPageException;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

public class BidDAO {
    private static final BidDAO instance = new BidDAO();

    private BidDAO() {
    }

    public static BidDAO getInstance() {
        return instance;
    }

    /**
     * Bid mapping method
     * @param rs is a ResultSet of SQL query
     * @return Bid object
     * @throws SQLException in case of any SQL exception
     * @throws ErrorPageException in case of Type or Status getters exception
     */
    private Bid generateBid(ResultSet rs) throws SQLException, ErrorPageException {
        DirectionDAO dao = DirectionDAO.getInstance();

        int id = rs.getInt("id");
        int accountId = rs.getInt("id");
        Direction to = dao.getDirection(rs.getInt("direction_id_to"));
        Direction from = dao.getDirection(rs.getInt("direction_id_from"));
        Status status = Status.getStatus(rs.getString("status"));
        Type type = Type.getType(rs.getString("type"));
        double weight = rs.getDouble("weight");
        String volume = rs.getString("volume");
        Date createdDate = rs.getDate("created_date");
        Date deliveryDate = rs.getDate("delivery_date");
        BigDecimal total = rs.getBigDecimal("total_price");

        return new Bid(id, accountId, from, to, status, type, weight, volume, deliveryDate, createdDate, total);
    }

    /**
     * DB Bid getter using id
     * @param id is an id of a Bid
     * @return Bid object
     * @throws ErrorPageException in case of any exception
     */
    public Bid getBidById(int id) throws ErrorPageException {
        String queue = "SELECT * FROM bid WHERE id=?";
        Bid result = null;

        Connection con = ConnectionPull.getConnection();
        try (PreparedStatement ps = con.prepareStatement(queue)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = generateBid(rs);
                }
            }

        } catch (SQLException | ErrorPageException exception) {
            throw new ErrorPageException();
        }

        return result;
    }

    /**
     * DB bid status update
     * @param bid_id is an id of a bid
     * @param status is a status to set
     * @throws ErrorPageException in case of any exception
     */
    public void updateBidStatus(int bid_id, Status status) throws ErrorPageException {
        String queue = "UPDATE bid SET status=? WHERE id=?";

        Connection con = ConnectionPull.getConnection();
        try (PreparedStatement ps = con.prepareStatement(queue)) {
            con.setAutoCommit(false);
            ps.setString(1, status.name());
            ps.setInt(2, bid_id);

            ps.executeUpdate();
            con.commit();
        } catch (SQLException sqlException) {
            try {
                con.rollback();
            } catch (SQLException throwables) {
                throw new ErrorPageException();
            }
            throw new ErrorPageException();
        }
    }

    /**
     * DB bid insertion method
     * @param account_id - id of user
     * @param direction_id_from - id of departure
     * @param direction_id_to - id of destination
     * @param status - bid status
     * @param type - bid type
     * @param weight - bid weight
     * @param volume - bid volume
     * @param deliveryDate - date of expected delivery
     * @param totalPrice - total price of the bid
     */
    public void insertBid(int account_id, int direction_id_from, int direction_id_to, Status status, Type type,
                          double weight, String volume, Date deliveryDate, BigDecimal totalPrice) {
        String queue =
                "INSERT INTO bid (account_id, direction_id_from, direction_id_to, status, type," +
                        " weight, volume, delivery_date, total_price)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection con = ConnectionPull.getConnection();
        try (PreparedStatement ps = con.prepareStatement(queue)) {
            con.setAutoCommit(false);

            ps.setInt(1, account_id);
            ps.setInt(2, direction_id_from);
            ps.setInt(3, direction_id_to);
            ps.setString(4, status.name());
            ps.setString(5, type.name());
            ps.setDouble(6, weight);
            ps.setString(7, volume);
            ps.setDate(8, deliveryDate);
            ps.setBigDecimal(9, totalPrice);

            ps.execute();

            con.commit();
        } catch (SQLException sqlException) {
            try {
                con.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            sqlException.printStackTrace();

        }
    }

    /**
     * Pagination pages count counter
     * @param queue - SQL queue
     * @return count of pages for queue
     * @throws ErrorPageException in case of any exception
     */
    private int getPagesCount(String queue) throws ErrorPageException {
        int count = -1;

        Connection con = ConnectionPull.getConnection();
        try (PreparedStatement ps = con.prepareStatement(queue)) {

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count(id)");
                }
            }

        } catch (SQLException sqlException) {
            throw new ErrorPageException();
        }

        if (count % 10 == 0) {
            return count / 10;
        } else {
            return count / 10 + 1;
        }
    }

    /**
     * Pages count for all bids
     * @param filter is a filter for SQL query
     * @return count of pages
     * @throws ErrorPageException in case of any exception
     */
    public int getPagesCountAll(String filter) throws ErrorPageException {
        String queue = "SELECT COUNT(id) FROM bid " + filter;
        return getPagesCount(queue);
    }

    /**
     * Pages count for users bids
     * @param filter is a filter for SQL query
     * @return count of pages
     * @throws ErrorPageException in case of any exception
     */
    public int getPagesCountUser(int id, String filter) throws ErrorPageException {
        String queue = "SELECT COUNT(id) FROM bid where account_id=? " + filter;
        int count = -1;

        Connection con = ConnectionPull.getConnection();
        try (PreparedStatement ps = con.prepareStatement(queue)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("count(id)");
                }
            }

        } catch (SQLException sqlException) {
            throw new ErrorPageException();
        }

        if (count % 10 == 0) {
            return count / 10;
        } else {
            return count / 10 + 1;
        }
    }

    /**
     * Pages count for active bids
     * @param filter is a filter for SQL query
     * @return count of pages
     * @throws ErrorPageException in case of any exception
     */
    public int getPagesCountManager(String filter) throws ErrorPageException {
        String queue = "SELECT COUNT(id) FROM bid where status='open' or status='payed' or status='validated' " + filter;
        return getPagesCount(queue);
    }

    /**
     * Bids list for one page getter
     * @param pageNum is a number of current page
     * @param filter is a filter for SQL query
     * @return list of bids
     * @throws ErrorPageException in case of any exception
     */
    public List<Bid> getPaginationPageAll(int pageNum, String filter) throws ErrorPageException {
        String queue = "SELECT * FROM bid " + filter + " ORDER BY created_date desc LIMIT ?, 10";
        List<Bid> result = new LinkedList<>();

        Connection con = ConnectionPull.getConnection();
        try (PreparedStatement ps = con.prepareStatement(queue)) {
            ps.setInt(1, 10 * (pageNum - 1));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(generateBid(rs));
                }
            }

        } catch (SQLException | ErrorPageException sqlException) {
            throw new ErrorPageException();
        }

        return result;
    }

    /**
     * Bids list for one page getter
     * @param pageNum is a number of current page
     * @param filter is a filter for SQL query
     * @return list of bids
     * @throws ErrorPageException in case of any exception
     */
    public List<Bid> getPaginationPageManager(int pageNum, String filter) throws ErrorPageException {
        String queue = "SELECT * FROM bid WHERE status='open' or status='validated' or status='payed' " + filter + " ORDER BY created_date desc LIMIT ?, 10";
        List<Bid> result = new LinkedList<>();

        Connection con = ConnectionPull.getConnection();
        try (PreparedStatement ps = con.prepareStatement(queue)) {
            ps.setInt(1, 10 * (pageNum - 1));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(generateBid(rs));
                }
            }

        } catch (SQLException | ErrorPageException sqlException) {
            throw new ErrorPageException();
        }

        return result;
    }

    /**
     * Bids list for one page getter
     * @param id is a user id
     * @param currentPage is a number of current page
     * @param filter is a filter for SQL query
     * @return list of bids
     * @throws ErrorPageException in case of any exception
     */
    public List<Bid> getPaginationPageUser(int id, int currentPage, String filter) throws ErrorPageException {
        String queue = "SELECT * FROM bid WHERE account_id=? " + filter + "order by created_date desc LIMIT ?, 10";

        List<Bid> result = new LinkedList<>();

        Connection con = ConnectionPull.getConnection();
        try (PreparedStatement ps = con.prepareStatement(queue)) {
            ps.setInt(1, id);
            ps.setInt(2, (currentPage - 1) * 10);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(generateBid(rs));
                }
            }

        } catch (SQLException | ErrorPageException exception) {
            throw new ErrorPageException();
        }

        return result;
    }
}
