package com.easy_way_bank.repositories;

import com.easy_way_bank.models.Payment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Integer> {
    @Modifying
    @Query(value = "insert into bank.payments(account_id, beneficiary, beneficiary_account_no, amount, reference_no, status, reason_code, created_at)" +
            "values(:account_id, :beneficiary, :beneficiary_account_no, :amount, :reference_no, :status, :reason_code, :created_at)",nativeQuery = true)
    @Transactional
    void makePayment(@Param("account_id")int account_id,
                     @Param("beneficiary")String beneficiary,
                     @Param("beneficiary_account_no")String beneficiary_account_no,
                     @Param("amount")double amount,
                     @Param("reference_no")String reference_no,
                     @Param("status")String status,
                     @Param("reason_code")String reason_code,
                     @Param("created_at")LocalDateTime created_at);
}
