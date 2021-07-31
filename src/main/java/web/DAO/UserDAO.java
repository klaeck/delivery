package web.DAO;

import entity.User;
import enums.Role;
import exceptions.ErrorPageException;

import java.sql.*;

public class UserDAO {

    private static final UserDAO instance = new UserDAO();

    public static UserDAO getInstance() {
        return instance;
    }

    private UserDAO() {
    }

    /**
     * User mapping method
     * @param rs - result set of SQL query
     * @return generated User from a db
     * @throws SQLException in case of SQL exception
     * @throws ErrorPageException in case of Role.getRole() method exception
     */
    private User generateUser(ResultSet rs) throws SQLException, ErrorPageException {
        int userID = rs.getInt("id");
        String fname = rs.getString("first_name");
        String lname = rs.getString("last_name");
        String phone = rs.getString("phone");
        String password = rs.getString("password");
        Role role = Role.getRole(rs.getString("role"));
        return new User(userID, fname, lname, phone, password, role);
    }

    /**
     * DB user getter using phone
     * @param phone is users phone used as a login
     * @return User row from DB
     * @throws ErrorPageException in case of any exception
     */
    public User getUserByPhone(String phone) throws ErrorPageException {
        String query = "SELECT * FROM account WHERE phone=?";
        User user = null;
        Connection con = ConnectionPull.getConnection();

        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, phone);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = generateUser(rs);

                } else {
                    System.err.printf("No SQL response in UserDAO.getUserByLogin() " +
                            "method with parameters login: %s", phone);
                    throw new ErrorPageException();
                }
            }

        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }

        return user;
    }

    /**
     * DB user validation method
     * @param phone - user phone used as a login
     * @param password - user password
     * @return boolean of validation
     * @throws ErrorPageException in case of sql error
     */
    public boolean validateUser(String phone, String password) throws ErrorPageException {
        String queue = "SELECT * FROM account WHERE phone=? AND password=?";

        Connection con = ConnectionPull.getConnection();
        try (PreparedStatement ps = con.prepareStatement(queue)) {
            ps.setString(1, phone);
            ps.setString(2, String.valueOf(password.hashCode()));

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException sqlException) {
            throw new ErrorPageException();
        }
    }

    /**
     * DB user addition
     * @param fname is users first name
     * @param lname is users second name to insert
     * @param phone is users phone number
     * @param password is password to put in the DB
     */
    public void insertUser(String fname, String lname, String phone, String password) {
        String query = "INSERT INTO account (first_name, last_name, phone, password, role) values (?, ?, ?, ?, ?);";

        Connection con = ConnectionPull.getConnection();
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, phone);
            ps.setString(4, String.valueOf(password.hashCode()));
            ps.setString(5, Role.user.name());

            ps.executeUpdate();

        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }
    }

    /**
     * DB user flag if user with that number exists
     * @param phone is users phone
     * @return flag if exists
     */
    public boolean isExists(String phone) {
        String query = "SELECT * FROM account WHERE phone=?";

        Connection con = ConnectionPull.getConnection();
        try (PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, phone);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return false;
    }
}