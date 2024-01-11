package hu.okosotthon.back.session;

import hu.okosotthon.back.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SessionFilter extends OncePerRequestFilter {
    @Autowired
    private SessionRegistery sessionRegistery;
    @Autowired
    private UsersService usersService;

//    @Autowired
//    public SessionFilter(final SessionRegistery sessionRegistery, final UsersService usersService) {
//        this.sessionRegistery = sessionRegistery;
//        this.usersService = usersService;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String sessionId = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(sessionId == null || sessionId.isEmpty()){
            filterChain.doFilter(request,response);
            return;
        }

        final String username = sessionRegistery.getUsernameForSession(sessionId);
        if(username == null){
            filterChain.doFilter(request,response);
            return;
        }

        final UserDetails user = usersService.loadUserByUsername(username);

        final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request,response);
    }
}
