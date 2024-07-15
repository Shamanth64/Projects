package com.easy_way_bank.controllers;


import com.easy_way_bank.models.User;
import com.easy_way_bank.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(@RequestParam("email")String email,
                        @RequestParam("password")String password,
                        @RequestParam("_token")String token,
                        Model model,
                        HttpSession session) {

        if (email.isEmpty() || password.isEmpty()) {
            model.addAttribute("error","Username or password is empty");
            return "login";
        }

        String getEmailInDatabase = userRepository.getUserEmail(email);
        if (getEmailInDatabase != null) {
            String getPasswordInDatabase = userRepository.getUserPassword(getEmailInDatabase);
            if (!BCrypt.checkpw(password, getPasswordInDatabase)) {
                model.addAttribute("error","Incorrect username or password");
                return "login";
            }
        } else {
            model.addAttribute("error","Something went wrong. Please contact support.");
            return "error";
        }

        int verified = userRepository.isVerified(getEmailInDatabase);
        if (verified != 1) {
            model.addAttribute("error","Not yet verified, please check email and verify.");
            return "login";
        }

        User user = userRepository.getUserDetails(getEmailInDatabase);
        session.setAttribute("user", user);
        session.setAttribute("token", token);
        session.setAttribute("authenticated", true);

        return "redirect:/app/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("logged_out", "Logged out successfully");
        return "redirect:/login";
    }

}

