package com.easy_way_bank.repositories;

import com.easy_way_bank.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query(value = "select email from bank.users where email= :email", nativeQuery = true)
    String getUserEmail(@Param("email")String email);
    @Query(value = "select password from bank.users where email= :email", nativeQuery = true)
    String getUserPassword(@Param("email")String email);
    @Query(value = "select verified from bank.users where email= :email", nativeQuery = true)
    int isVerified(@Param("email")String email);
    @Query(value = "select * from bank.users where email= :email", nativeQuery = true)
    User getUserDetails(@Param("email")String email);
    @Modifying
    @Query(value = "insert into users (first_name, last_name, email, password, token, code) values" + "(:first_name, :last_name, :email, :password, :token, :code)",
            nativeQuery = true)
    @Transactional
    void registerUser(@Param("first_name")String first_name,
                      @Param("last_name")String last_name,
                      @Param("email")String email,
                      @Param("password")String password,
                      @Param("token")String token,
                      @Param("code")int code);
    @Modifying
    @Query(value = "update bank.users set token=null, code=null, verified=1, verified_at=NOW(), updated_at=NOW() where token= :token and code= :code", nativeQuery = true)
    @Transactional
    void verifyAccount(@Param("token")String token, @Param("code")String code);
    @Query(value = "select token from bank.users where token= :token", nativeQuery = true)
    String checkToken(@Param("token")String token);
}
