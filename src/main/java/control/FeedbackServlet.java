package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.bean.Feedback;
import model.bean.User;
import model.dao.FeedbackDAO;

import java.io.IOException;
import java.sql.SQLException;

//Servlet per aggiungere un feedback
@WebServlet("/addfeedback")
public class FeedbackServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Recupero user dalla sessione
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            // L'utente non è autenticato redirige alla pagina di login
            resp.sendRedirect("register-login.jsp");
            return;
        }

        //Recupera i parametri dalla richiesta
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        int score = Integer.parseInt(req.getParameter("score"));
        int userId = currentUser.getIdUser();
        int productId = Integer.parseInt(req.getParameter("productId"));

        //Crea il bean del feedback
        Feedback feedback = new Feedback();
        feedback.setTitle(title);
        feedback.setDescription(description);
        feedback.setScore(score);
        feedback.setUser(userId);
        feedback.setProduct(productId);

        FeedbackDAO feedbackDAO = new FeedbackDAO();

        //Memorizza il feedback nel database
        try {
            feedbackDAO.doSave(feedback);
            resp.sendRedirect("product?id=" + productId); // Redirige alla pagina del prodotto
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
