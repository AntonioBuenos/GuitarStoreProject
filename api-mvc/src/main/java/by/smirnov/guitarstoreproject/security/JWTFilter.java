package by.smirnov.guitarstoreproject.security;

import by.smirnov.guitarstoreproject.service.UserDetailsSecurityService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.smirnov.guitarstoreproject.constants.SecurityConstants.*;

@RequiredArgsConstructor
@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserDetailsSecurityService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(AUTH_HEADER_NAME);

        if (authHeader !=null && !authHeader.isBlank() && authHeader.startsWith(AUTH_HEADER_STARTS)){
            String jwt = authHeader.substring(7);

            if(jwt.isBlank()){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, INVALID_HEADER_TOKEN_MESSAGE);
            } else {
                try{
                    String username = jwtUtil.ValidateTokenAndRetrieveClaim(jwt);
                    UserDetails userDetails = service.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                                    userDetails.getAuthorities());

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (JWTVerificationException e){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, INVALID_TOKEN_MESSAGE);
                }

            }
        }
        filterChain.doFilter(request, response);
    }
}
