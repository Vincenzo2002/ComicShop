package control;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.bean.Product;
import model.dao.Cart;
import model.dao.CartEntry;
import model.dao.ProductDAO;

import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;

//Servlet per aggiungere un prodotto al carrello
@WebServlet("/addtocart")
public class AddToCartServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Se i parametri della request sono null invia un messaggio di erroe
        if(req.getParameter("pId") == null || req.getParameter("q") == null){
            resp.sendError(400);
            return;
        }

        try{
            //Recupera i parametri dalla servlet
            int productId = Integer.parseInt(req.getParameter("pId"));
            int productQuantity = Integer.parseInt(req.getParameter("q"));
            Product product = new ProductDAO().doRetrieveByKey(productId); //Recupera il prodotto dal database in base all'id preso dalla request
            Cart cart = (Cart) req.getSession(true).getAttribute("cart"); //Recupera il carrello dalla sessione se è un null lo crea
            if(cart == null){
                cart = new Cart();
            }
            //Se la quantita richesta e maggiore di quella disponibile imposta la quantita massima disponibile
            CartEntry c = new CartEntry(product, productQuantity <= product.getQuantity() ? productQuantity : product.getQuantity());
            cart.set(c);//Aggiunge prodotto al carrello
            req.getSession().setAttribute("cart", cart);
            resp.setContentType("application/json");
            resp.getWriter().print(new Gson().toJson(cart, Cart.class)); //Invia una rappresentazione json del carrello
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
