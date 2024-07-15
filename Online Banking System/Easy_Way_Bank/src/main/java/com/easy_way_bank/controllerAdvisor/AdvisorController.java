package com.easy_way_bank.controllerAdvisor;

import com.easy_way_bank.models.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AdvisorController {
    @ModelAttribute("registerUser")
    public User getUserDefaults() {
        return new User();
    }
}
