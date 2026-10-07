package com.todocodeacademy.BlogCode.config.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.todocodeacademy.BlogCode.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Collection;

public class JwtTokenValidator extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORITIES_CLAIM = "authorities";
    private static final String COOKIE_JWT = "jwt";

    private final JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().startsWith("/auth/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith(BEARER_PREFIX)) {
            String token = header.substring(BEARER_PREFIX.length());
            if (!autenticarConToken(token)) {
                writeUnauthorized(response);
                return;
            }
        } else {
            autenticarDesdeCookie(request);
        }

        filterChain.doFilter(request, response);
    }

    private void autenticarDesdeCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, COOKIE_JWT);
        if (cookie != null) {
            // Cookie inválida o vencida: se sigue como anónimo para que el entry point redirija a /login
            autenticarConToken(cookie.getValue());
        }
    }

    private boolean autenticarConToken(String token) {
        try {
            DecodedJWT decodedJWT = jwtUtils.verifyToken(token);
            String username = jwtUtils.extractUsername(decodedJWT);
            String authoritiesClaim = decodedJWT.getClaim(AUTHORITIES_CLAIM).asString();

            if (username == null || username.isBlank() || authoritiesClaim == null) {
                return false;
            }

            Collection<? extends GrantedAuthority> authorities =
                    AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim);
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;

        } catch (JWTVerificationException ex) {
            return false;
        }
    }

    private void writeUnauthorized(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"error\":\"Invalid or expired token\"}");
    }

}
