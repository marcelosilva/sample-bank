package com.marcelosilva.samples.banking.validator.impl;

import com.marcelosilva.samples.banking.exception.BusinessException;
import com.marcelosilva.samples.banking.validator.AmountValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PositiveAmountValidator implements AmountValidator {

    @Override
    public void validate(BigDecimal amount) throws BusinessException {

        if (BigDecimal.ZERO.compareTo(amount) >= 0) {
            throw new BusinessException("Amount value should be greater than 0");
        }

    }
}
