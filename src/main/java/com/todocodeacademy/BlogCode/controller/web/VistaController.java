package com.todocodeacademy.BlogCode.controller.web;

import com.todocodeacademy.BlogCode.dto.AuthenticationRequest;
import com.todocodeacademy.BlogCode.service.UserDetailServiceImp;
import com.todocodeacademy.BlogCode.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class VistaController {

    private static final String COOKIE_JWT = "jwt";
    private static final String CLAIM_AUTHORITIES = "authorities";

    private final UserDetailServiceImp userDetailService;
    private final JwtUtils jwtUtils;

    public VistaController(UserDetailServiceImp userDetailService, JwtUtils jwtUtils) {
        this.userDetailService = userDetailService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,
                                @RequestParam String password,
                                HttpServletResponse response) {
        try {
            String token = userDetailService.login(new AuthenticationRequest(username, password)).token();
            guardarTokenEnCookie(response, token);
            return "redirect:" + rutaSegunRol(token);
        } catch (AuthenticationException ex) {
            return "redirect:/login?error";
        }
    }

    @GetMapping("/author")
    public String vistaAutor(Authentication authentication, Model model) {
        agregarUsuario(model, authentication);
        return "author";
    }

    @GetMapping("/user")
    public String vistaUsuario(Authentication authentication, Model model) {
        agregarUsuario(model, authentication);
        return "user";
    }

    private void guardarTokenEnCookie(HttpServletResponse response, String token) {
        // Cookie de sesión: el vencimiento lo controla el propio JWT
        ResponseCookie cookie = ResponseCookie.from(COOKIE_JWT, token)
                .httpOnly(true)
                .sameSite("Strict")
                .path("/")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private String rutaSegunRol(String token) {
        List<String> authorities = obtenerAuthorities(token);
        if (authorities.contains("ROLE_ADMIN")) {
            return "/admin";
        }
        if (authorities.contains("ROLE_AUTHOR")) {
            return "/author";
        }
        if (authorities.contains("ROLE_USER")) {
            return "/user";
        }
        return "/login?sinRol";
    }

    private List<String> obtenerAuthorities(String token) {
        String claim = jwtUtils.verifyToken(token).getClaim(CLAIM_AUTHORITIES).asString();
        return List.of(claim.split(","));
    }

    private void agregarUsuario(Model model, Authentication authentication) {
        model.addAttribute("usuario", authentication.getName());
    }

}