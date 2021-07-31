package web.servlet;

import entity.RateDistance;
import entity.RateVolume;
import entity.RateWeight;
import web.DAO.RateDistanceDAO;
import web.DAO.RateVolumeDAO;
import web.DAO.RateWeightDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/home/rates_serv")
public class RatesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RateDistanceDAO distanceDAO = RateDistanceDAO.getInstance();
        List<RateDistance> distances = distanceDAO.getAllDistanceRates();

        RateVolumeDAO volumeDAO = RateVolumeDAO.getInstance();
        List<RateVolume> volumes = volumeDAO.getAllVolumeRates();

        RateWeightDAO weightDAO = RateWeightDAO.getInstance();
        List<RateWeight> weights = weightDAO.getAllWeightRates();

        req.setAttribute("distances", distances);
        req.setAttribute("volumes", volumes);
        req.setAttribute("weights", weights);

        getServletContext().getRequestDispatcher("/home/rates").forward(req, resp);
    }
}
