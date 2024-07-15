package com.easy_way_bank.repositories;

import com.easy_way_bank.models.TransactionHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactHistoryRepository extends CrudRepository<TransactionHistory, Integer> {
    @Query(value = "select * from bank.v_transaction_history where user_id= :user_id",nativeQuery = true)
    List<TransactionHistory> getTransactionRecordsById(@Param("user_id")int user_id);
}
