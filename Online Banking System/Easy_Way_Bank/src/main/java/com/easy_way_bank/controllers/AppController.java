package com.easy_way_bank.controllers;

import com.easy_way_bank.models.Account;
import com.easy_way_bank.models.PaymentHistory;
import com.easy_way_bank.models.TransactionHistory;
import com.easy_way_bank.models.User;
import com.easy_way_bank.repositories.AccountRepository;
import com.easy_way_bank.repositories.PaymentHistoryRepository;
import com.easy_way_bank.repositories.TransactHistoryRepository;
import com.easy_way_bank.repositories.TransactRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/app")
public class AppController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;
    @Autowired
    private TransactHistoryRepository transactHistoryRepository;

    @GetMapping("/dashboard")
    public ModelAndView getDashboard(HttpSession session) {
        ModelAndView getDashboardPage = new ModelAndView("dashboard");
        User user = (User) session.getAttribute("user");

        List<Account> getUserAccount = accountRepository.getUserAccountById(user.getUser_id());
        BigDecimal totalAccountBalance = accountRepository.getTotalBalance(user.getUser_id());

        getDashboardPage.addObject("userAccounts", getUserAccount);
        getDashboardPage.addObject("totalBalance", totalAccountBalance);

        return getDashboardPage;
    }

    @GetMapping("/payment_history")
    public ModelAndView getPaymentHistory(HttpSession session) {
        ModelAndView getPaymentHistoryPage = new ModelAndView("paymentHistory");
        User user = (User) session.getAttribute("user");
        List<PaymentHistory> userPaymentHistory = paymentHistoryRepository.getPaymentRecordsById(user.getUser_id());
        getPaymentHistoryPage.addObject("payment_history", userPaymentHistory);
        return  getPaymentHistoryPage;
    }

    @GetMapping("/transaction_history")
    public ModelAndView getTransactHistory(HttpSession session) {
        ModelAndView getTransactHistoryPage = new ModelAndView("transactHistory");
        User user = (User) session.getAttribute("user");
        List<TransactionHistory> userTransactHistory = transactHistoryRepository.getTransactionRecordsById(user.getUser_id());
        getTransactHistoryPage.addObject("transaction_history", userTransactHistory);
        return  getTransactHistoryPage;
    }
}
