package com.example.compl.Controllers;

import com.example.compl.Models.StatmentModel;
import com.example.compl.Models.UserModel;
import com.example.compl.Service.StatmentService;
import com.example.compl.Service.UserService;
import com.example.compl.repo.StatmentRepo;
import com.example.compl.repo.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class MainController {
    private final UserService userService;
    private final StatmentService statmentService;
    private final StatmentRepo statmentRepo;
    private final UserRepo userRepo;

    @GetMapping("/")
    public String homepage(Model model, Principal principal) {
        UserModel user = userRepo.findByUsername(principal.getName());
        model.addAttribute("stats", statmentService.findByUser(user));
        model.addAttribute("username", principal.getName());
        return "home";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userModel", new UserModel());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserModel userModel) {
        userService.store(userModel);
        return "redirect:/login";
    }

    @GetMapping("/add")
    public String add() {
        return "add";
    }

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password. Please try again.");
        } else if (error == null && model.getAttribute("errorMessage") == null) {
            // Handle InternalAuthenticationServiceException here
            model.addAttribute("errorMessage", "Internal server error. Please try again later.");
        }
        return "login";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("stat", statmentRepo.findById(id).orElse(null));
        model.addAttribute("statuses", statmentService.getAllStatus());
        return "edit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("stats", statmentRepo.findAll());
        return "admin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/edit/{id}")
    public String adminEditStatement(@PathVariable Long id, Model model) {
        model.addAttribute("stat", statmentRepo.findById(id).orElse(null));
        model.addAttribute("statuses", statmentService.getAllStatus());
        return "admin_edit";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute StatmentModel data) {
        statmentService.store(data);
        return "redirect:/";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute StatmentModel data) {
        statmentService.edit(data);
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/admin/edit")
    public String adminEdit(@ModelAttribute StatmentModel data) {
        statmentService.adminEdit(data);
        return "redirect:/admin";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout"; // перенаправление на страницу входа с параметром logout
    }
}
