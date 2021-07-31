package web.servlet;

import entity.Bid;
import entity.User;
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

@WebServlet("/account/bids_serv")
public class BidsServlet extends HttpServlet {
    private String filterFrom = "0";
    private String filterTo = "0";
    private String filterStatus = "any";
    private String filterType = "any";

    protected static void changeStatus(BidDAO bidDAO, List<Bid> bids, Date dateNow) throws ErrorPageException {
        for (Bid bid : bids) {
            if ((bid.getStatus() == Status.open || bid.getStatus() == Status.validated)
                    && bid.getDeliveryDate().compareTo(dateNow) < 0) {
                bidDAO.updateBidStatus(bid.getId(), Status.canceled);
            }

            if (bid.getStatus() == Status.paid && bid.getDeliveryDate().compareTo(dateNow) < 0) {
                bidDAO.updateBidStatus(bid.getId(), Status.delivered);
            }
        }
    }

    protected static String getFilter(String filterFrom, String filterTo, String filterStatus, String filterType) {
        StringBuilder filter = new StringBuilder();

        if (!filterFrom.equalsIgnoreCase("0")) {
            filter.append(" AND direction_id_from=").append(filterFrom).append(" ");
        }
        if (!filterTo.equalsIgnoreCase("0")) {
            filter.append(" AND direction_id_to=").append(filterTo).append(" ");
        }
        if (!filterStatus.equalsIgnoreCase("any")) {
            filter.append(" AND status='").append(filterStatus).append("' ");
        }
        if (!filterType.equalsIgnoreCase("any")) {
            filter.append(" AND type='").append(filterType).append("' ");
        }

        return filter.toString();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            BidDAO bidDAO = BidDAO.getInstance();
            User user = (User) req.getSession().getAttribute("user");

            int currentPage;
            String filter = getFilter(filterFrom, filterTo, filterStatus, filterType);


            if (req.getParameter("page") == null) {
                currentPage = 1;
            } else {
                currentPage = Integer.parseInt(req.getParameter("page"));
            }


            List<Bid> bids = bidDAO.getPaginationPageUser(user.getId(), currentPage, filter);
            int pagesCount = bidDAO.getPagesCountUser(user.getId(), filter);
            Date dateNow = new Date(System.currentTimeMillis());

            BidsServlet.changeStatus(bidDAO, bids, dateNow);

            req.setAttribute("pagesCount", pagesCount);
            req.setAttribute("currentPage", currentPage);

            req.setAttribute("bids", bids);
        } catch (ErrorPageException pageException) {
            getServletContext().getRequestDispatcher("/error").forward(req, resp);
        }

        getServletContext().getRequestDispatcher("/account/bids").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("change_bid") != null) {
            try {
                int id = Integer.parseInt(req.getParameter("change_bid"));
                BidDAO bidDAO = BidDAO.getInstance();
                Date dateNow = new Date(System.currentTimeMillis());
                Bid bid = bidDAO.getBidById(id);

                if (bid.getDeliveryDate().compareTo(dateNow) > 0) {
                    bidDAO.updateBidStatus(id, Status.paid);
                } else {
                    bidDAO.updateBidStatus(id, Status.canceled);
                }

                resp.sendRedirect("/delivery_war/account/bids_serv");

            } catch (ErrorPageException e) {
                getServletContext().getRequestDispatcher("/error").forward(req, resp);
            }

        } else if (req.getParameter("get") != null) {
            resp.sendRedirect("/delivery_war/account/bids_serv");

        } else if (req.getParameter("from") != null) {
            filterFrom = req.getParameter("from");
            filterTo = req.getParameter("to");
            filterStatus = req.getParameter("status");
            filterType = req.getParameter("type");

            resp.sendRedirect("/delivery_war/account/bids_serv");

        } else {
            getServletContext().getRequestDispatcher("/error").forward(req, resp);
        }
    }
}
