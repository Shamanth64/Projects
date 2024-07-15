package com.easy_way_bank.controllers;

import com.easy_way_bank.helpers.GenAccountNumber;
import com.easy_way_bank.models.User;
import com.easy_way_bank.repositories.AccountRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    @PostMapping("/create_account")
    public String createAccount(@RequestParam("account_name")String accountName,
                                @RequestParam("account_type")String accountType,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {
        if (accountName.isEmpty() || accountType.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Account name or account type cannot be empty");
            return "redirect:/app/dashboard";
        }

        User user = (User)session.getAttribute("user");

        int setAccountNumber = GenAccountNumber.generateAccountNumber();
        String bankAccountNumber = Integer.toString(setAccountNumber);

        accountRepository.createBankAccount(user.getUser_id(), bankAccountNumber, accountName, accountType);
        redirectAttributes.addFlashAttribute("success", "Account successfully created");
        return "redirect:/app/dashboard";
    }
}
