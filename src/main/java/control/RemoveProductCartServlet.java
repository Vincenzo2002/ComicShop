package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.Cart;
import model.dao.CartEntry;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

@WebServlet("/removecart")
public class RemoveProductCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("pId"));

        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            CartEntry entry = cart.findById(productId);
            if (entry != null) {
                // Rimuovi il prodotto impostando la quantità a 0
                cart.removeProduct(entry.getProduct().getIdProduct());

                // Rimuovi il prodotto dalla sessione
                request.getSession().setAttribute("cart", cart);

                // Calcola i totali aggiornati
                float total = calculateCartTotal(cart);

                // Imposta la risposta
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                out.write("{\"cartTotal\": " + total + "}");
                out.close();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found in cart.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cart not found.");
        }
    }

    private float calculateCartTotal(Cart cart) {
        float total = 0;
        for (Iterator<CartEntry> it = cart.getProduct(); it.hasNext(); ) {
            CartEntry entry = it.next();
            total += entry.getProduct().getPrice() * entry.getQuantity();
        }
        return total;
    }
}
