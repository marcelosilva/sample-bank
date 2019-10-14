package com.marcelosilva.samples.banking.service.impl;

import com.marcelosilva.samples.banking.annotations.LogTransaction;
import com.marcelosilva.samples.banking.dao.AccountDAO;
import com.marcelosilva.samples.banking.enums.TransactionType;
import com.marcelosilva.samples.banking.exception.BusinessException;
import com.marcelosilva.samples.banking.exception.NoFundsException;
import com.marcelosilva.samples.banking.exception.NotFoundException;
import com.marcelosilva.samples.banking.model.Account;
import com.marcelosilva.samples.banking.service.AccountOperationService;
import com.marcelosilva.samples.banking.validator.AmountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class AccountOperationServiceImpl implements AccountOperationService {

    private AccountDAO accountDAO;
    private AmountValidator amountValidator;

    public AccountOperationServiceImpl(@Autowired AccountDAO accountDAO,
                                       @Autowired AmountValidator amountValidator) {
        this.accountDAO = accountDAO;
        this.amountValidator = amountValidator;
    }

    @Override
    @LogTransaction(transactionType = TransactionType.WITHDRAW)
    public Account withdraw(Long accountId, BigDecimal amount) throws NoFundsException, BusinessException {

        this.amountValidator.validate(amount);

        Account account = this.getAccountAndLockOrThrowNotFound(accountId);

        if (!account.hasEnoughFunds(amount)) {
            throw new NoFundsException("This account does not have enough funds to do this operation");
        }

        account.setBalance(account.getBalance().subtract(amount));
        this.accountDAO.save(account);

        return account;
    }

    @Override
    @LogTransaction(transactionType = TransactionType.DEPOSIT)
    public Account deposit(Long accountId, BigDecimal amount) throws BusinessException {

        this.amountValidator.validate(amount);
        Account account = this.getAccountAndLockOrThrowNotFound(accountId);
        account.setBalance(account.getBalance().add(amount));
        this.accountDAO.save(account);
        return account;

    }

    private Account getAccountAndLockOrThrowNotFound(Long accountId) {
        return accountDAO.findAndLockById(accountId)
                .orElseThrow(() -> new NotFoundException("This account does not exist"));
    }

}
