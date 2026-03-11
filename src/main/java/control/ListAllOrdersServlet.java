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

//Servelet per avere tutti gli ordini effettuati
@WebServlet("/admin/listallorders")
public class ListAllOrdersServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //Repupero parametri skip e limit dalla request per la paginazione
            String skipParam = req.getParameter("skip");
            String limitParam = req.getParameter("limit");

            //Se sono null imposto skip a 0 e limit a 10
            int skip = (skipParam != null && !skipParam.isEmpty()) ? Integer.parseInt(skipParam) : 0;
            int limit = (limitParam != null && !limitParam.isEmpty()) ? Integer.parseInt(limitParam) : 10;

            if (limit == 0) limit = 10;

            //Recupera tutti gli ordini e imposta i parametri nella request
            ArrayList<Order> orders = (ArrayList<Order>) new OrderDAO().doRetrieveAll("", skip, limit);
            req.setAttribute("orders", orders);
            req.setAttribute("skip", skip);
            req.setAttribute("limit", limit);
            getServletContext().getRequestDispatcher("/pages/admin/listallorders.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

