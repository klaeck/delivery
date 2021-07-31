package web.servlet;

import entity.*;
import enums.Status;
import enums.Type;
import exceptions.ErrorPageException;
import web.DAO.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;

@WebServlet("/account/create_bid_ver")
public class CreateBidServlet extends HttpServlet {
    protected BigDecimal calculateTotalPrice(Direction departure, Direction destination, double weight, RateVolume volume) throws ErrorPageException {
        RateDistanceDAO distanceDAO = RateDistanceDAO.getInstance();
        RateWeightDAO weightDAO = RateWeightDAO.getInstance();

        BigDecimal distancePrice = distanceDAO.getPrice(
                Direction.getDistance(departure, destination));
        BigDecimal weightPrice = weightDAO.getPrice(weight);

        return distancePrice.add(weightPrice).add(volume.getPrice());
    }

    private String generateVolumeString(RateVolume volume) {
        return volume.getHeight() + "x" + volume.getLength() + "x" + volume.getWidth();
    }

    private Date generateDeliveryTime(Direction d1, Direction d2) {
        return new Date(System.currentTimeMillis() + 84_400_000 + (long) (Direction.getDistance(d1, d2) / (0.00002361)));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            BidDAO bidDAO = BidDAO.getInstance();
            DirectionDAO directionDAO = DirectionDAO.getInstance();
            RateVolumeDAO volumeDAO = RateVolumeDAO.getInstance();

            Direction departure = directionDAO.getDirection(
                    Integer.parseInt(req.getParameter("departure")));
            if (departure == null) {
                throw new ErrorPageException();
            }

            Direction destination = directionDAO.getDirection(
                    Integer.parseInt(req.getParameter("destination")));
            if (destination == null) {
                throw new ErrorPageException();
            }

            RateVolume volume = volumeDAO.getVolume(
                    Integer.parseInt(req.getParameter("volume")));
            if (volume == null) {
                throw new ErrorPageException();
            }

            double weight = Double.parseDouble(req.getParameter("weight"));
            if (weight < 0.1) {
                throw new ErrorPageException();
            }

            BigDecimal totalPrice = calculateTotalPrice(departure, destination, weight, volume);
            if (totalPrice.compareTo(new BigDecimal(0)) <= 0) {
                throw new ErrorPageException();
            }

            Type type = Type.getType(req.getParameter("type"));
            String volumeString = generateVolumeString(volume);
            Date deliveryDate = generateDeliveryTime(departure, destination);
            System.out.println(deliveryDate);
            User user = (User) req.getSession().getAttribute("user");

            bidDAO.insertBid(user.getId(), departure.getId(), destination.getId(),
                    Status.open, type, weight, volumeString, deliveryDate, totalPrice);

            req.getRequestDispatcher("bids_serv?get=1").forward(req, resp);

        } catch (ErrorPageException e) {
            req.getRequestDispatcher("../error").forward(req, resp);
        }
    }
}
