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
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.regex.Pattern;

//Servlet per la registrazione di un nuovo utente
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Recupero dei parametri dalla richesta inviati dal form
        String firstName = req.getParameter("fname");
        String lastName = req.getParameter("lname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String provincia = req.getParameter("provincia");
        String via = req.getParameter("via");
        String civico = req.getParameter("civico");
        String cap = req.getParameter("cap");
        String citta = req.getParameter("citta");

        // Validazione dei parametri recuperati dalla richiesta
        StringBuilder errorMsg = new StringBuilder();
        boolean isValid = true;

        if (firstName == null || firstName.trim().isEmpty()) {
            isValid = false;
            errorMsg.append("Nome obbligatorio");
        }

        if (lastName == null || lastName.trim().isEmpty()) {
            isValid = false;
            errorMsg.append("Cognome obbligatorio");
        }

        if (email == null || email.trim().isEmpty()) {
            isValid = false;
            errorMsg.append("Email obbligatoria");
        } else if (!isValidEmail(email)) {
            isValid = false;
            errorMsg.append("Formato email non valido");
        }

        if (password == null || password.trim().isEmpty()) {
            isValid = false;
            errorMsg.append("Password obbligatoria<br>");
        } else if (!isValidPassword(password)) {
            isValid = false;
            errorMsg.append("Password deve contenere almeno 8 caratteri, un carattere maiuscolo e un numero");
        }

        if (provincia == null || provincia.trim().isEmpty()) {
            isValid = false;
            errorMsg.append("Provincia obbligatoria");
        }

        if (via == null || via.trim().isEmpty()) {
            isValid = false;
            errorMsg.append("Via obbligatoria");
        }

        if (civico == null || civico.trim().isEmpty()) {
            isValid = false;
            errorMsg.append("Civico obbligatorio");
        }

        if (cap == null || cap.trim().isEmpty()) {
            isValid = false;
            errorMsg.append("CAP obbligatorio");
        } else if (!cap.matches("\\d{5}")) {
            isValid = false;
            errorMsg.append("Formato CAP non valido deve contenere 5 caratteri");
        }

        if (citta == null || citta.trim().isEmpty()) {
            isValid = false;
            errorMsg.append("Citta obbligatiria");
        }

        // Se la validazione fallisce, invia un messaggio di errore
        if (!isValid) {
            req.setAttribute("errorMsg", errorMsg.toString());
            req.getRequestDispatcher("/pages/register-login.jsp").forward(req, resp);
            return;
        }

        // Creazione oggetto User
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setProvincia(provincia);
        user.setVia(via);
        user.setCivico(civico);
        user.setCap(cap);
        user.setCitta(citta);

        // Salvataggio dell'utente nel database
        try {
            new UserDAO().doSave(user);
            resp.sendRedirect(getServletContext().getContextPath() + "/pages/post-signup.jsp");
        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                e.printStackTrace();
                req.setAttribute("errorMsg", "Email già utilizzata");
                req.getRequestDispatcher("/pages/register-login.jsp").forward(req, resp);
            } else {
                e.printStackTrace();
            }
        }
    }

    // Metodo di validazione dell'email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // La regex controlla se la password contiene almeno:
        // - 8 caratteri
        // - Una lettera maiuscola
        // - Un numero
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        return pattern.matcher(password).matches();
    }

}



