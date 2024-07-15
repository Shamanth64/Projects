package com.easy_way_bank.repositories;

import com.easy_way_bank.models.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
    @Query(value = "select * from bank.accounts where user_id = :user_id",nativeQuery = true)
    List<Account> getUserAccountById(@Param("user_id")int user_id);

    @Query(value = "select sum(balance) from bank.accounts where user_id = :user_id",nativeQuery = true)
    BigDecimal getTotalBalance(@Param("user_id")int user_id);

    @Modifying
    @Query(value = "update bank.accounts set balance= :new_balance where account_id= :account_id",nativeQuery = true)
    @Transactional
    void changeAccountBalanceById(@Param("new_balance") double new_balance,
                                  @Param("account_id")int account_id);

    @Query(value = "select balance from bank.accounts where user_id = :user_id and account_id = :account_id",nativeQuery = true)
    double getAccountBalance(@Param("user_id")int user_id,
                             @Param("account_id")int account_id);

    @Modifying
    @Query(value = "insert into bank.accounts(user_id, account_number, account_name, account_type, balance) values(:user_id, :account_number, :account_name, :account_type, 0.00) ", nativeQuery = true)
    @Transactional
    void createBankAccount(@Param("user_id")int user_id,
                           @Param("account_number")String account_number,
                           @Param("account_name")String account_name,
                           @Param("account_type")String account_type);
}
