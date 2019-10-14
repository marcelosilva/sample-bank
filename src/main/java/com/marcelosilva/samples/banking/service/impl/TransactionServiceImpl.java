package com.marcelosilva.samples.banking.service.impl;

import com.marcelosilva.samples.banking.dao.TransactionDAO;
import com.marcelosilva.samples.banking.model.Transaction;
import com.marcelosilva.samples.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private TransactionDAO transactionDAO;

    public TransactionServiceImpl(@Autowired TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    @Override
    public List<Transaction> findByAccount(Long accountId) {
        return transactionDAO.findByAccount_Id(accountId);
    }
}
