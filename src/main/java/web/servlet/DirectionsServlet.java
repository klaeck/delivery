package web.servlet;

import entity.Direction;
import web.DAO.DirectionDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/home/directions_serv")
public class DirectionsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DirectionDAO directionBID = DirectionDAO.getInstance();

        List<Direction> directions = directionBID.getAllDirections();

        req.setAttribute("directions", directions);

        getServletContext().getRequestDispatcher("/home/directions").forward(req, resp);
    }
}
