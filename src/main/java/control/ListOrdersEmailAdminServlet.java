package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.bean.Order;
import model.bean.User;
import model.dao.OrderDAO;
import model.dao.UserDAO;

import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.ArrayList;

//Servel per avere tutti gli ordini di un utente
@WebServlet("/admin/listordersemail")
public class ListOrdersEmailAdminServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //Recupero parametri skip e limit per la paginazione
            int skip = Integer.parseInt(req.getParameter("skip"));
            int limit = Integer.parseInt(req.getParameter("limit"));
            if (limit == 0) limit = 10;

            String emailUser = req.getParameter("email");
            User user = new UserDAO().doRetrieveByEmail(emailUser);
            ArrayList<Order> orders = new ArrayList<>();

            if (user != null) {
                orders = (ArrayList<Order>) new OrderDAO().doRetrieveByUser(user, "Date DESC", skip, limit);
                req.setAttribute("client", user);
            }

            req.setAttribute("orders", orders);
            req.setAttribute("skip", skip);
            req.setAttribute("limit", limit);
            getServletContext().getRequestDispatcher("/pages/admin/listforuser.jsp").forward(req, resp);
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
