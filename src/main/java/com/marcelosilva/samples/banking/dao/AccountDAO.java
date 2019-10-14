package com.marcelosilva.samples.banking.dao;

import com.marcelosilva.samples.banking.model.Account;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface AccountDAO extends CrudRepository<Account, Long> {

    List<Account> findByUser_Id(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Account a where a.id = :id")
    Optional<Account> findAndLockById(@Param("id") Long id);

}
