package com.todocodeacademy.BlogCode.controller.web;

import com.todocodeacademy.BlogCode.service.IAuthorService;
import com.todocodeacademy.BlogCode.service.IPermissionService;
import com.todocodeacademy.BlogCode.service.IPostService;
import com.todocodeacademy.BlogCode.service.IRoleService;
import com.todocodeacademy.BlogCode.service.IUserSecService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminVistaController {

    private final IUserSecService userSecService;
    private final IRoleService roleService;
    private final IPermissionService permissionService;
    private final IAuthorService authorService;
    private final IPostService postService;

    public AdminVistaController(IUserSecService userSecService,
                                IRoleService roleService,
                                IPermissionService permissionService,
                                IAuthorService authorService,
                                IPostService postService) {
        this.userSecService = userSecService;
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.authorService = authorService;
        this.postService = postService;
    }

    @GetMapping("/admin")
    public String vistaAdmin(Authentication authentication, Model model) {
        agregarUsuarioLogueado(model, authentication);
        agregarListados(model);
        return "admin";
    }

    private void agregarUsuarioLogueado(Model model, Authentication authentication) {
        model.addAttribute("usuario", authentication.getName());
    }

    private void agregarListados(Model model) {
        model.addAttribute("usuarios", userSecService.findAll());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("permisos", permissionService.findAll());
        model.addAttribute("autores", authorService.findAll());
        model.addAttribute("posts", PostsOrdenados.recientesPrimero(postService.findAll()));
    }

}
