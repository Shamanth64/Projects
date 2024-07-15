package com.easy_way_bank.controllers;


import com.easy_way_bank.models.User;
import com.easy_way_bank.repositories.AccountRepository;
import com.easy_way_bank.repositories.PaymentRepository;
import com.easy_way_bank.repositories.TransactRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;


@Controller
@RequestMapping("/transact")
public class TransactController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private TransactRepository transactRepository;

    LocalDateTime createdAt;
    double currentBalance, newBalance;

   @PostMapping("/deposit")
    public String deposit(@RequestParam("deposit_amount")String depositAmount,
                          @RequestParam("account_id")String accountID,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {

       if (depositAmount.isEmpty() || accountID.isEmpty()){
           redirectAttributes.addFlashAttribute("error","Deposit amount or accountID cannot be empty");
           return "redirect:/app/dashboard";
       }
       User user = (User) session.getAttribute("user");
       int acc_id = Integer.parseInt(accountID);
       double depositAmountValue = Double.parseDouble(depositAmount);
       currentBalance = accountRepository.getAccountBalance(user.getUser_id(), acc_id);
       if (depositAmountValue == 0) {
           redirectAttributes.addFlashAttribute("error","Deposit amount cannot be zero");
           return "redirect:/app/dashboard";
       }

       newBalance = currentBalance + depositAmountValue;
       accountRepository.changeAccountBalanceById(newBalance, acc_id);
       createdAt = LocalDateTime.now();
       transactRepository.logTransaction(acc_id,"deposit", depositAmountValue, "online", "success", "Deposit successful", createdAt);
       redirectAttributes.addFlashAttribute("success","Amount deposited successfully");
       return "redirect:/app/dashboard";
   }

   @PostMapping("/transfer")
    public String transfer(@RequestParam("transfer_from")String transfer_from,
                           @RequestParam("transfer_to")String  transfer_to,
                           @RequestParam("payment_amount")String transfer_amount,
                           HttpSession session,
                           RedirectAttributes redirectAttributes){
       if (transfer_from.isEmpty() || transfer_to.isEmpty() || transfer_amount.isEmpty()) {
           redirectAttributes.addFlashAttribute("error","The account transferring from and to and along with the amount cannot be empty.");
           return "redirect:/app/dashboard";
       }

       int transferFromId = Integer.parseInt(transfer_from);
       int transferToId = Integer.parseInt(transfer_to);
       double transferAmount = Double.parseDouble(transfer_amount);

       if (transferFromId == transferToId) {
           redirectAttributes.addFlashAttribute("error","Cannot transfer to same account. Please select the appropriate account to perform transfer");
           return "redirect:/app/dashboard";
       }

       if (transferAmount == 0) {
           redirectAttributes.addFlashAttribute("error","Cannot transfer an amount of 0. Please enter a value greater than 0");
           return "redirect:/app/dashboard";
       }

       User user = (User) session.getAttribute("user");

       double currentBalanceOfTransferringFrom = accountRepository.getAccountBalance(user.getUser_id(), transferFromId);

       if (currentBalanceOfTransferringFrom < transferAmount) {
           redirectAttributes.addFlashAttribute("error","Insufficient balance");
           createdAt = LocalDateTime.now();
           transactRepository.logTransaction(transferFromId, "transfer", transferAmount, "online", "failed", "Insufficient balance", createdAt);
           return "redirect:/app/dashboard";
       }

       double currentBalanceOfTransferringTo = accountRepository.getAccountBalance(user.getUser_id(), transferToId);

       double newBalanceOfAccountTransferringFrom = currentBalanceOfTransferringFrom - transferAmount;
       double newBalanceOfAccountTransferringTo = currentBalanceOfTransferringTo + transferAmount;
       accountRepository.changeAccountBalanceById(newBalanceOfAccountTransferringFrom, transferFromId);

       accountRepository.changeAccountBalanceById(newBalanceOfAccountTransferringTo, transferToId);

       createdAt = LocalDateTime.now();
       transactRepository.logTransaction(transferFromId, "transfer", transferAmount, "online", "success", "Transfer successful", createdAt);
       redirectAttributes.addFlashAttribute("success","Transfer completed successfully");
       return "redirect:/app/dashboard";
   }

   @PostMapping("/withdraw")
    public String withdraw(@RequestParam("withdraw_amount")String withdrawAmount,
                           @RequestParam("account_id")String accountID,
                           HttpSession session,
                           RedirectAttributes redirectAttributes){
        if (withdrawAmount.isEmpty() || accountID.isEmpty()) {
            redirectAttributes.addFlashAttribute("error","Withdraw amount and account cannot be empty");
            return "redirect:/app/dashboard";
        }

        double withdraw_amount = Double.parseDouble(withdrawAmount);
        int account_id = Integer.parseInt(accountID);

        if (withdraw_amount == 0) {
            redirectAttributes.addFlashAttribute("error","Withdraw amount cannot be 0");
            return "redirect:/app/dashboard";
        }

        User user = (User)session.getAttribute("user");

        currentBalance = accountRepository.getAccountBalance(user.getUser_id(), account_id);

       if (currentBalance < withdraw_amount) {
           redirectAttributes.addFlashAttribute("error","Insufficient balance");
           createdAt = LocalDateTime.now();
           transactRepository.logTransaction(account_id, "withdraw", withdraw_amount, "online", "failed", "Insufficient balance", createdAt);
           return "redirect:/app/dashboard";
       }

        newBalance = currentBalance - withdraw_amount;

        accountRepository.changeAccountBalanceById(newBalance, account_id);

       createdAt = LocalDateTime.now();
       transactRepository.logTransaction(account_id, "withdraw", withdraw_amount, "online", "success", "Withdraw successful", createdAt);
       redirectAttributes.addFlashAttribute("success","Amount withdrew successfully");
       return "redirect:/app/dashboard";
   }

   @PostMapping("/payment")
    public String payment(@RequestParam("beneficiary")String beneficiary,
                          @RequestParam("account_number")String account_number,
                          @RequestParam("account_id")String account_id,
                          @RequestParam("reference")String reference,
                          @RequestParam("payment_amount")String payment_amount,
                          HttpSession session,
                          RedirectAttributes redirectAttributes){

       if (beneficiary.isEmpty() || account_number.isEmpty() || account_id.isEmpty() || payment_amount.isEmpty()) {
           redirectAttributes.addFlashAttribute("error","Beneficiary or account number or account or payment cannot be empty.");
           return "redirect:/app/dashboard";
       }

       int accountID = Integer.parseInt(account_id);
       double paymentAmount = Double.parseDouble(payment_amount);

       if (paymentAmount == 0) {
           redirectAttributes.addFlashAttribute("error","Amount cannot be 0.");
           return "redirect:/app/dashboard";
       }

       User user = (User)session.getAttribute("user");

       currentBalance = accountRepository.getAccountBalance(user.getUser_id(), accountID);

       if (currentBalance < paymentAmount) {
           String reasonCode = "Payment could not be processed";
           createdAt = LocalDateTime.now();
           paymentRepository.makePayment(accountID, beneficiary, account_number, paymentAmount, reference, "failed", reasonCode, createdAt);
           createdAt = LocalDateTime.now();
           transactRepository.logTransaction(accountID, "payment", paymentAmount, "online", "failed", "Insufficient balance", createdAt);
           redirectAttributes.addFlashAttribute("error","Insufficient balance");
           return "redirect:/app/dashboard";
       }

       newBalance = currentBalance - paymentAmount;

       String reasonCode = "Payment processed successfully";
       createdAt = LocalDateTime.now();
       paymentRepository.makePayment(accountID, beneficiary, account_number, paymentAmount, reference, "success", reasonCode, createdAt);

       accountRepository.changeAccountBalanceById(newBalance, accountID);

       createdAt = LocalDateTime.now();
       transactRepository.logTransaction(accountID, "payment", paymentAmount, "online", "success", "Payment successful", createdAt);

       redirectAttributes.addFlashAttribute("success",reasonCode);
       return "redirect:/app/dashboard";
   }
}
