package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.bean.User;
import model.dao.UserDAO;

import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Recupero email e password dalla request inviati attraverso il form
        String email = req.getParameter("loginEmail");
        String password = req.getParameter("loginPassword");

        // Verifica che l'email e la password sono presenti
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            req.setAttribute("errorMsg", "Email e password sono obbligatori.");
            req.getRequestDispatcher("/pages/register-login.jsp").forward(req, resp);
            return;
        }

        try {
            //Recupero user dal database in base all'email e password inseriti
            User user = new UserDAO().doRetrieveByEmailAndPassword(email, password);
            if (user == null) {
                req.setAttribute("errorMsg", "Email o password errati.");
                req.getRequestDispatcher("/pages/register-login.jsp").forward(req, resp);
                return;
            }
            req.getSession(true).setAttribute("user", user);
            req.getSession().setAttribute("redirect", null);

            //In base al tipo di utente lo indirizza alla pagina appropriata
            if (user.isAdmin()) {
                resp.sendRedirect(getServletContext().getContextPath() + "/pages/admin/index.jsp");
            } else {
                resp.sendRedirect(getServletContext().getContextPath() + "/pages/index.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("errorMsg", "Si è verificato un errore durante il login. Riprova più tardi.");
            req.getRequestDispatcher("/pages/register-login.jsp").forward(req, resp);
        }
    }
}

