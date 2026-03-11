package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.bean.Order;
import model.dao.OrderDAO;

import java.io.IOException;
import java.io.Serial;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

//Servlet per avere gli ordini in un intervallo di tempo
@WebServlet("/admin/listordersdate")
public class ListOrdersDataAdminServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Recupera i parametri di paginazione dalla richiesta HTTP
            int skip = Integer.parseInt(req.getParameter("skip"));
            int limit = Integer.parseInt(req.getParameter("limit"));
            if (limit == 0) limit = 10;  // Imposta un limite di default a 10 se il valore di limit è 0

            // Converte i parametri di data (start e end) da stringa a oggetto Date
            java.util.Date utilDateStart = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("dateStart"));
            java.util.Date utilDateEnd = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("dateEnd"));

            // Converte le date Date in java.sql.Date per l'uso con JDBC
            java.sql.Date dateStart = new java.sql.Date(utilDateStart.getTime());
            java.sql.Date dateEnd = new java.sql.Date(utilDateEnd.getTime());

            // Recupera gli ordini dal database utilizzando le date e i parametri di paginazione
            ArrayList<Order> orders = (ArrayList<Order>) new OrderDAO().doRetrieveByDate(dateStart, dateEnd, skip, limit);

            // Imposta gli ordini e i parametri di paginazione come attributi della richiesta
            req.setAttribute("orders", orders);
            req.setAttribute("skip", skip);
            req.setAttribute("limit", limit);

            // Inoltra la richiesta e la risposta alla JSP per la visualizzazione degli ordini
            getServletContext().getRequestDispatcher("/pages/admin/listfordate.jsp").forward(req, resp);
            return;
        } catch (ParseException e) {
            resp.getWriter().println("Errore conversione data");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

