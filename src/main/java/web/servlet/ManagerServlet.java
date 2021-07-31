package web.servlet;

import entity.Bid;
import enums.Status;
import exceptions.ErrorPageException;
import web.DAO.BidDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/manager_serv")
public class ManagerServlet extends HttpServlet {
    private String filterFrom = "0";
    private String filterTo = "0";
    private String filterStatus = "any";
    private String filterType = "any";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            BidDAO bidDAO = BidDAO.getInstance();
            int currentPage;
            String filter = BidsServlet.getFilter(filterFrom, filterTo, filterStatus, filterType);

            if (req.getParameter("page") == null) {
                currentPage = 1;
            } else {
                currentPage = Integer.parseInt(req.getParameter("page"));
            }

            List<Bid> bids = bidDAO.getPaginationPageManager(currentPage, filter);
            int pagesCount = bidDAO.getPagesCountManager(filter);
            Date dateNow = new Date(System.currentTimeMillis());

            BidsServlet.changeStatus(bidDAO, bids, dateNow);

            req.setAttribute("mBids", bids);
            req.setAttribute("pagesCount", pagesCount);
            req.setAttribute("currentPage", currentPage);

            BidsServlet.changeStatus(bidDAO, bids, dateNow);

            req.setAttribute("mBids", bids);
            req.getRequestDispatcher("/manager").forward(req, resp);

        } catch (ErrorPageException pageException) {
            getServletContext().getRequestDispatcher("/error").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("validate") != null) {
            try {
                int id = Integer.parseInt(req.getParameter("validate"));

                BidDAO.getInstance().updateBidStatus(id, Status.validated);

                resp.sendRedirect("/delivery_war/manager_serv");
            } catch (ErrorPageException | NumberFormatException pageException) {
                resp.sendRedirect("/delivery_war/error");
            }

        } else if (req.getParameter("cancel") != null) {
            try {
                int id = Integer.parseInt(req.getParameter("cancel"));

                BidDAO.getInstance().updateBidStatus(id, Status.canceled);

                resp.sendRedirect("/delivery_war/manager_serv");
            } catch (ErrorPageException | NumberFormatException pageException) {
                resp.sendRedirect("/delivery_war/error");
            }

        } else if (req.getParameter("from") != null) {
            filterFrom = req.getParameter("from");
            filterTo = req.getParameter("to");
            filterStatus = req.getParameter("status");
            filterType = req.getParameter("type");

            resp.sendRedirect("/delivery_war/manager_serv");

        } else if (req.getParameter("get") != null) {
            resp.sendRedirect("/delivery_war/manager_serv");

        } else {
            resp.sendRedirect("/delivery_war/error");
        }


    }
}
