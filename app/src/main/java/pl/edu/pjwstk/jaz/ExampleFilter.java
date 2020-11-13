package pl.edu.pjwstk.jaz;

import org.springframework.http.HttpStatus;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ExampleFilter extends HttpFilter {
    private final UserSession userSession;

    public ExampleFilter(UserSession userSession) {this.userSession = userSession;}

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (isUserLogged() || isSiteAllowed(req)) {
            chain.doFilter(req, res);
        } else {
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
        }}

        private boolean isSiteAllowed(HttpServletRequest req) {
            return false;
        }

        private boolean isUserLogged() {
            return userSession.isLoggedIn();
        }

}
