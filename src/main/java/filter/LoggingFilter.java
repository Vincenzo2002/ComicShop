package filter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/* Questo filtro registra informazioni su ogni richiesta HTTP ricevuta dal server.
   Le informazioni includono l'indirizzo IP del client, il protocollo HTTP, il metodo della richiesta
   e l'URL della richiesta.*/
@WebFilter("/*")
public class LoggingFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String ip = request.getRemoteAddr();
        final String protocol = request.getProtocol();
        String method = ((HttpServletRequest) request).getMethod();
        String path = ((HttpServletRequest) request).getRequestURL().toString();
        Logger.getLogger("ComicShop").log(Level.INFO, protocol + " " + method + " " + path + " FROM " + ip + "");
        chain.doFilter(request, response);
    }

}
