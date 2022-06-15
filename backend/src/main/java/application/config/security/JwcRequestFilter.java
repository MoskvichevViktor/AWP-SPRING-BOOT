package application.config.security;

import application.utils.jwtsecuriru.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j

public class JwcRequestFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;

    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHandler = request.getHeader("Authorization");
        String userName = null;
        String jwt = null;

        if (authHandler != null && authHandler.startsWith("Bearer ")) {
            jwt = authHandler.substring(7);
            try {
                userName = jwtTokenUtil.getUserNameFromToken(jwt);
            } catch (ExpiredJwtException e) {
                log.debug("Token expired");
            }
        }
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userName, null, jwtTokenUtil.
                    getRoles(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
            );
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        try {
            filterChain.doFilter(request, response);
        } catch (ServletException e) {
            System.out.println("application/config/security/JwcRequestFilter.filterChain.doFilter(request, response) class исключение: " + e);
            resolver.resolveException(request, response, null, e);
        }
    }

}
