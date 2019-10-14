package com.marcelosilva.samples.banking.service;

import com.marcelosilva.samples.banking.model.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> findByAccount(Long accountId);

}
