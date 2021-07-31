package web.servlet;

import exceptions.ErrorPageException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/language")
public class LanguageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String locale = req.getParameter("locale");

            if (locale == null || (!locale.equalsIgnoreCase("en") && !locale.equalsIgnoreCase("ru"))) {
                locale = "en";
            }

            req.getSession().setAttribute("locale", locale);

            if (req.getSession().getAttribute("prevURL") == null) {
                throw new ErrorPageException();
            }

            resp.sendRedirect("/delivery_war/" + req.getSession().getAttribute("prevURL"));
        } catch (ErrorPageException | IOException e){
            resp.sendRedirect("/delivery_war/error");
        }
    }
}
