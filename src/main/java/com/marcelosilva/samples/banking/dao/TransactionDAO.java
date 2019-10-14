package com.marcelosilva.samples.banking.dao;

import com.marcelosilva.samples.banking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionDAO extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccount_Id(Long accountId);

}
