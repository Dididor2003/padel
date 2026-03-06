package com.padelhub.padel_app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Intercepta cada petició HTTP abans que arribi als controladors.
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils; 						// per validar i llegir el token

    @Autowired
    private UserDetailsService userDetailsService;	// per carregar l'usuari de BD

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

    	// llegeix la capçalera Authorization
        String header = request.getHeader("Authorization");

        // comprova que el token existeix i te format correcte
        if (header != null && header.startsWith("Bearer ")) {
        	// extrau el token
            String token = header.substring(7);

            // valida el token i autentica l'usuari
            if (jwtUtils.isTokenValid(token)) {
                String email = jwtUtils.extractEmail(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // deixa continuar la petició
        filterChain.doFilter(request, response);
    }
}
