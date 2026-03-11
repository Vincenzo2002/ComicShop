package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.bean.Product;
import model.dao.ProductDAO;

import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.ArrayList;

//Servlet per avere tutti i prodotti per il catalogo admin
@WebServlet("/admin/products")
public class ProductsServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();
        ArrayList<Product> products = new ArrayList<>();

        try{
            //Recupera i prodotti dal database
            products = (ArrayList<Product>) productDAO.doRetrieveAll(null);
        } catch (SQLException e) {
            resp.getWriter().println("Error");
            e.printStackTrace();
            return;
        }
        //Imposta il parametro nella request
        req.setAttribute("products", products);
        getServletContext().getRequestDispatcher("/pages/products.jsp").forward(req, resp);
    }
}
