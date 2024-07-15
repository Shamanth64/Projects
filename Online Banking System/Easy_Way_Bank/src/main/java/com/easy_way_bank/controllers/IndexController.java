package com.easy_way_bank.controllers;

import com.easy_way_bank.helpers.Token;
import com.easy_way_bank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public ModelAndView getIndex() {
        return new ModelAndView("index");
    }

    @GetMapping("/login")
    public ModelAndView getLogin() {
        ModelAndView getLoginPage = new ModelAndView("login");
        String token = Token.generateToken();
        getLoginPage.addObject("token", token);
        return getLoginPage;
    }

    @GetMapping("/error")
    public ModelAndView getError() {
        return new ModelAndView("error");
    }

    @GetMapping("/verify")
    public ModelAndView getVerification(@RequestParam("token")String token, @RequestParam("code")String code) {
        ModelAndView getVerifyPage;

        String dbToken = userRepository.checkToken(token);

        if (dbToken == null){
            getVerifyPage = new ModelAndView("error");
            getVerifyPage.addObject("error", "This session has expired");
            return getVerifyPage;
        }

        userRepository.verifyAccount(token, code);
        getVerifyPage = new ModelAndView("login");
        getVerifyPage.addObject("success", "Account Verified. Please proceed to log in!");
        return getVerifyPage;
    }

    @GetMapping("/register")
    public ModelAndView getRegister() {
        return new ModelAndView("register");
    }
}
