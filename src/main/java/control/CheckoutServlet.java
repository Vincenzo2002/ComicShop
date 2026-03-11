package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.bean.Order;
import model.bean.Product;
import model.bean.ProductOrder;
import model.bean.User;
import model.dao.*;

import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if(req.getSession().getAttribute("user") != null){
            if(cart != null){
                // Itera attraverso gli elementi del carrello
                Iterator<CartEntry> iterator = cart.getProduct();
                while(iterator.hasNext()){
                    CartEntry entry = iterator.next();
                    try {
                        // Ottiene la quantità disponibile del prodotto dal database
                        int quantity = new ProductDAO().getQuantityProduct(entry.getProduct().getIdProduct());
                        // Controlla se la quantità nel carrello supera quella disponibile
                        if(entry.getQuantity() > quantity){
                            // Imposta un messaggio di errore e inoltra alla pagina di errore
                            req.setAttribute("errorMsg", "Quantita prodotto " + entry.getProduct().getName() + " insufficiente");
                            getServletContext().getRequestDispatcher("/pages/error/errorPage.jsp").forward(req, resp);
                            return;
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                // Crea un ordine
                Order order = createOrder(req);
                try{
                    // Salva l'ordine e ottieni il suo ID
                    int idOrder = new OrderDAO().doSaveAndGetId(order);
                    order.setIdOrder(idOrder);
                    // Crea la lista di prodotti associati all'ordine
                    ArrayList<ProductOrder> productOrders = createProducts(cart, idOrder);
                    ProductOrderDAO productOrderDAO = new ProductOrderDAO();
                    // Salva ogni prodotto dell'ordine e aggiorna la quantità disponibile
                    for(ProductOrder p : productOrders){
                        productOrderDAO.doSave(p);
                        new ProductDAO().decreaseQuantity(p.getProduct(), p.getQuantity());
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                cart.clear();
            }
            resp.sendRedirect(getServletContext().getContextPath() + "/pages/post-checkout.jsp");
        }else{
            // Reindirizza alla pagina di login o registrazione se l'utente non è autenticato
            resp.sendRedirect(getServletContext().getContextPath() + "/pages/register-login.jsp");
        }
    }

    // Crea un oggetto Order con le informazioni dell'ordine
    private Order createOrder(HttpServletRequest req){
        Order order = new Order();
        order.setDateOrder(new Date()); // Imposta la data dell'ordine
        order.setState("Completato"); // Imposta lo stato dell'ordine
        order.setIdUser(((User) req.getSession().getAttribute("user")).getIdUser()); // Imposta l'ID utente
        return order;
    }

    // Crea una lista di oggetti ProductOrder a partire dal carrello e dall'ID dell'ordine
    private ArrayList<ProductOrder> createProducts(Cart cart, int idOrder) {
        ArrayList<ProductOrder> result = new ArrayList<>();
        Iterator<CartEntry> iterator = cart.getProduct();

        while(iterator.hasNext()){
            CartEntry entry = iterator.next();
            ProductOrder productOrder = new ProductOrder();
            productOrder.setProduct(entry.getProduct());
            productOrder.setPrice(entry.getProduct().getPrice());
            productOrder.setQuantity(entry.getQuantity());
            productOrder.setIdOrder(idOrder);
            result.add(productOrder);
        }
        return result;
    }
}

