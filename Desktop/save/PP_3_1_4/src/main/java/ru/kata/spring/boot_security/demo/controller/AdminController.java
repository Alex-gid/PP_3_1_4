package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.servise.RoleService;
import ru.kata.spring.boot_security.demo.servise.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/")
    public String findAll(Principal principal, Model model) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        model.addAttribute("users", userService.getAll());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin";
    }

    @GetMapping("/user-create")
    public String createUser(ModelMap modelMap, @ModelAttribute("user") User user) {
        modelMap.addAttribute("roles", roleService.getAllRoles());
        return "admin";
    }

    @PostMapping("/")
    public String saveUser (User user) {
        userService.add(user);
        return "redirect:/admin/";
    }


    @GetMapping("/{id}/update")
    public String updateUserForm(Model role, @PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.show(id));
        role.addAttribute("roles", roleService.getAllRoles());
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String updateUser(ModelMap model, @ModelAttribute ("user") User user, @PathVariable("id") Long id) {
        model.addAttribute("roles", roleService.getAllRoles());
        userService.update(user, id);
        return "redirect:/admin/";
    }

    @PostMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/";
    }

}
