package com.med.backend.config.security.filter;

import com.med.backend.persistence.entity.User;
import com.med.backend.persistence.entity.security.JwtToken;
import com.med.backend.persistence.repository.security.JwtTokenRepository;
import com.med.backend.service.UserService;
import com.med.backend.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Extraer el token JWT del encabezado de la solicitud
        String jwt = jwtService.extractJwtFromRequest(request);

        if (!StringUtils.hasText(jwt)) {
            // No se encontró token en la solicitud, continuar sin autenticación
            filterChain.doFilter(request, response);
            return;
        }

        // Verificar si el token es válido y no ha expirado
        Optional<JwtToken> token = jwtTokenRepository.findByToken(jwt);
        boolean isValid = validateToken(token);

        if (!isValid) {
            // Token no válido, continuar sin configurar el contexto de seguridad
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer el nombre de usuario del token JWT
        String username = jwtService.extractUsername(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Cargar los detalles del usuario y configurar la autenticación en el contexto
            User user = userService.findOneByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found. Username: " + username));

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Configurar la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // Continuar con el filtro
        filterChain.doFilter(request, response);
    }

    private boolean validateToken(Optional<JwtToken> optionalJwtToken) {
        if (!optionalJwtToken.isPresent()) {
            System.out.println("El token no existe o no fue generado en nuestro sistema");
            return false;
        }

        JwtToken token = optionalJwtToken.get();
        Date now = new Date();

        boolean isTokenValid = token.isValid();
        boolean isNotExpired = token.getExpiration().after(now);

        if (!isTokenValid) {
            System.out.println("El token no es válido.");
        }

        if (!isNotExpired) {
            System.out.println("El token ha expirado.");
        }

        boolean isValid = isTokenValid && isNotExpired;

        if (!isValid) {
            updateTokenStatus(token);
        }

        return isValid;
    }

    private void updateTokenStatus(JwtToken token) {
        token.setValid(false);
        jwtTokenRepository.save(token);
    }
}
