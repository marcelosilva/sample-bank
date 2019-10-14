package com.marcelosilva.samples.banking.service;

import com.marcelosilva.samples.banking.exception.BusinessException;
import com.marcelosilva.samples.banking.exception.NoFundsException;
import com.marcelosilva.samples.banking.model.Account;

import java.math.BigDecimal;

public interface AccountOperationService {

    Account withdraw(Long accountId, BigDecimal amount) throws NoFundsException, BusinessException;

    Account deposit(Long accountId, BigDecimal amount) throws BusinessException;

}
