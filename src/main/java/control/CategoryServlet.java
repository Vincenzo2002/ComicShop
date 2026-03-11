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
import java.util.Arrays;
import java.util.List;

//Servlet per avere i prodotti di un categoria
@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final List<String> VALID_CATEGORIES = Arrays.asList("Manga", "Fumetti", "Gadget");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Recupera i parametri dalla request
        String category = req.getParameter("category");
        int skip = Integer.parseInt(req.getParameter("skip"));
        int limit = Integer.parseInt(req.getParameter("limit"));

        if (category == null || !VALID_CATEGORIES.contains(category)) {
            // Imposta un messaggio di errore e reindirizza a una pagina di errore
            req.setAttribute("errorMsg", "Categoria non valida. Le categorie valide sono: Manga, Fumetti, Gadget.");
            getServletContext().getRequestDispatcher("/pages/error/errorPage.jsp").forward(req, resp);
        }

        try {
            ArrayList<Product> products;
            int numProducts;

            //Restituisce tutti i prodotti di una categoria
            products = (ArrayList<Product>) new ProductDAO().doRetrieveByCategory(category, skip, limit);
            numProducts = new ProductDAO().getNumProductByCategory(category);

            //Imposta i parametri nella request
            req.setAttribute("products", products);
            req.setAttribute("numProducts", numProducts);
            // Inoltra la richiesta e la risposta alla pagina JSP
            getServletContext().getRequestDispatcher("/pages/category.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

