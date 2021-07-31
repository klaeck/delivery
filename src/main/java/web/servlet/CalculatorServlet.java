package web.servlet;

import entity.Direction;
import entity.RateVolume;
import exceptions.ErrorPageException;
import web.DAO.DirectionDAO;
import web.DAO.RateVolumeDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/home/calculator_serv")
public class CalculatorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("directions", DirectionDAO.getInstance().getAllDirections());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            DirectionDAO directionDAO = DirectionDAO.getInstance();
            RateVolumeDAO volumeDAO = RateVolumeDAO.getInstance();

            Direction departure = directionDAO.getDirection(
                    Integer.parseInt(req.getParameter("departure")));
            Direction destination = directionDAO.getDirection(
                    Integer.parseInt(req.getParameter("destination")));
            double weight = Double.parseDouble(req.getParameter("weight"));

            RateVolume volume = volumeDAO.getVolume(
                    Integer.parseInt(req.getParameter("volume")));

            BigDecimal price = new CreateBidServlet().calculateTotalPrice(departure, destination, weight, volume);


            resp.sendRedirect("/delivery_war/home/calculator?price=" + price);
        } catch (ErrorPageException e) {
            resp.sendRedirect("/delivery_war/error");
        }

    }
}
