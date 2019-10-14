package com.marcelosilva.samples.banking.validator;

import com.marcelosilva.samples.banking.exception.BusinessException;

import java.math.BigDecimal;

public interface AmountValidator {

    void validate(BigDecimal amount) throws BusinessException;

}
