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

//Servelet per avere informazioni su un prodotto
@WebServlet("/product")
public class ProductServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Recupero id del prodotto dalla request
        String idProduct = req.getParameter("id");

        if(idProduct != null){
            try{
                //Recupero il prodotto dal database
                Product product = new ProductDAO().doRetrieveByKey(Integer.parseInt(idProduct));
                if(product == null){
                    req.setAttribute("errorMsg", "Id prodotto non valido");
                    getServletContext().getRequestDispatcher("/pages/error/errorPage.jsp").forward(req, resp);
                }
                req.setAttribute("product", product);
                getServletContext().getRequestDispatcher("/pages/product.jsp").forward(req, resp);
            } catch (NumberFormatException e) {
                resp.sendError(400);
            }catch (SQLException e){
                e.printStackTrace();
                resp.sendError(500);
            }
        }else{
            resp.sendRedirect(getServletContext().getContextPath() + "/pages/index.jsp");
        }
    }
}
