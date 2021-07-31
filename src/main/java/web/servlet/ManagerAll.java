package web.servlet;

import entity.Bid;
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

@WebServlet("/manager/manager_all_serv")
public class ManagerAll extends HttpServlet {
    private String filterFrom = "0";
    private String filterTo = "0";
    private String filterStatus = "any";
    private String filterType = "any";

    private String getFilter() {
        StringBuilder filter = new StringBuilder("where");
        int filtersCount = 0;

        if (!filterFrom.equalsIgnoreCase("0")) {
            ++filtersCount;
            filter.append(" direction_id_from=").append(filterFrom).append(" ");
        }

        if (!filterTo.equalsIgnoreCase("0")) {
            if (filtersCount == 0) {
                ++filtersCount;
                filter.append(" direction_id_to=").append(filterTo).append(" ");
            } else {
                filter.append(" AND direction_id_to=").append(filterTo).append(" ");
            }
        }
        if (!filterStatus.equalsIgnoreCase("any")) {
            if (filtersCount == 0) {
                ++filtersCount;
                filter.append(" status='").append(filterStatus).append("' ");
            } else {
                filter.append(" AND status='").append(filterStatus).append("' ");
            }
        }
        if (!filterType.equalsIgnoreCase("any")) {
            if(filtersCount == 0) {
                ++filtersCount;
                filter.append(" type='").append(filterType).append("' ");
            } else {
                filter.append(" AND type='").append(filterType).append("' ");
            }
        }

        return filter.toString().equalsIgnoreCase("where") ? "" : filter.toString();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            BidDAO bidDAO = BidDAO.getInstance();
            int currentPage;
            String filter = getFilter();

            if (req.getParameter("page") == null) {
                currentPage = 1;
            } else {
                currentPage = Integer.parseInt(req.getParameter("page"));
            }

            List<Bid> bids = bidDAO.getPaginationPageAll(currentPage, filter);
            int pagesCount = bidDAO.getPagesCountAll(filter);
            Date dateNow = new Date(System.currentTimeMillis());

            BidsServlet.changeStatus(bidDAO, bids, dateNow);

            req.setAttribute("mBids", bids);
            req.setAttribute("pagesCount", pagesCount);
            req.setAttribute("currentPage", currentPage);

            req.getRequestDispatcher("/manager/manager_all").forward(req, resp);
        } catch (ErrorPageException pageException) {
            getServletContext().getRequestDispatcher("/error").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("from") != null) {
            filterFrom = req.getParameter("from");
            filterTo = req.getParameter("to");
            filterStatus = req.getParameter("status");
            filterType = req.getParameter("type");

            resp.sendRedirect("/delivery_war/manager/manager_all_serv");

        } else {
            resp.sendRedirect("/delivery_war/error");
        }
    }
}
