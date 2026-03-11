package filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.bean.User;


/* Questo filtro garantisce che solo gli utenti autenticati con diritti di amministratore
   possano accedere a determinate pagine. Intercetta le richieste agli URL che corrispondono
   ai pattern "/pages/admin/*" e "/admin/*".
 */
@WebFilter(urlPatterns = { "/pages/admin/*", "/admin/*" })
public class AdminFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest filteredRequest = (HttpServletRequest) request;
        HttpServletResponse filteredResponse = (HttpServletResponse) response;
        User user = (User) filteredRequest.getSession().getAttribute("user");
        if (user == null) {
            filteredResponse.sendRedirect(filteredRequest.getContextPath() + "/pages/login.jsp");
            return;
        } else if (!user.isAdmin()) {
            filteredResponse.setStatus(403);
            filteredResponse.getWriter().println("Non hai i permessi per accedere a questa risorsa");
            return;
        }
        // Se l'utente è autenticato e ha diritti di amministratore, continua con la catena di filtri
        chain.doFilter(request, response);
    }

}
