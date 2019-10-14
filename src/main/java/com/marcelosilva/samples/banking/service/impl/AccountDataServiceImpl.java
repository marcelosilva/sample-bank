package com.marcelosilva.samples.banking.service.impl;

import com.marcelosilva.samples.banking.dao.AccountDAO;
import com.marcelosilva.samples.banking.model.Account;
import com.marcelosilva.samples.banking.model.User;
import com.marcelosilva.samples.banking.service.AccountDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AccountDataServiceImpl implements AccountDataService {

    private AccountDAO accountDAO;

    public AccountDataServiceImpl(@Autowired AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public Optional<Account> get(Long accountId) {
        return accountDAO.findById(accountId);
    }

    @Override
    public List<Account> find(User user) {
        return accountDAO.findByUser_Id(user.getId());
    }

    @Override
    public Account createByUser(User user) {
        return accountDAO.save(Account.builder().balance(BigDecimal.ZERO).user(user).build());
    }
}
