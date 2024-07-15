package com.easy_way_bank.controllers;

import com.easy_way_bank.helpers.HTML;
import com.easy_way_bank.helpers.Token;
import com.easy_way_bank.mailMessenger.MailMessenger;
import com.easy_way_bank.models.User;
import com.easy_way_bank.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Random;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ModelAndView register(@Valid@ModelAttribute("registerUser")User user,
                                 BindingResult result,
                                 @RequestParam("first_name") String first_name,
                                 @RequestParam("last_name") String last_name,
                                 @RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 @RequestParam("confirm_password") String confirm_password) throws MessagingException {
        ModelAndView registrationPage = new ModelAndView("register");

        if (result.hasErrors() && confirm_password.isEmpty()){
            registrationPage.addObject("confirm_pass","The confirm field is required");
            return registrationPage;
        }

        if (!password.equals(confirm_password)) {
            registrationPage.addObject("passwordMismatch","Passwords do not match");
            return registrationPage;
        }

        String token = Token.generateToken();

        Random rand = new Random();
        int bound = 123;
        int code = bound * rand.nextInt(bound);

        String emailBody = HTML.htmlEmailTemplate(token, code);

        String hashed_password = BCrypt.hashpw(password, BCrypt.gensalt());

        userRepository.registerUser(first_name, last_name, email, hashed_password, token, code);

        MailMessenger.htmlEmailMessenger("no-reply@easywaybank.com", email, "Verify Account", emailBody);

        String successMessage = "Account registered successfully. Please check you email and verify";
        registrationPage.addObject("success",successMessage);
        return registrationPage;
    }
}
