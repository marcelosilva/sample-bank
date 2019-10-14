package com.marcelosilva.samples.banking.interceptor;

import com.marcelosilva.samples.banking.annotations.LogTransaction;
import com.marcelosilva.samples.banking.dao.TransactionDAO;
import com.marcelosilva.samples.banking.model.Account;
import com.marcelosilva.samples.banking.model.Transaction;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Aspect
@Component
public class AccountOperationInterceptor {

    @Autowired
    TransactionDAO transactionDAO;

    @AfterReturning(pointcut = "execution(@com.marcelosilva.samples.banking.annotations.LogTransaction * *(..)) && @annotation(transactionAnnotation)",
            returning = "result")
    public void generateTransaction(JoinPoint joinPoint, LogTransaction transactionAnnotation, Account result) {

        BigDecimal amount = null;

        if (joinPoint.getArgs()[1] != null && joinPoint.getArgs()[1] instanceof BigDecimal) {
            amount = (BigDecimal) joinPoint.getArgs()[1];
        }

        Transaction transaction = Transaction.builder()
                .transactionType(transactionAnnotation.transactionType())
                .account(result)
                .executedAt(new Date())
                .value(amount).build();

        transactionDAO.save(transaction);

    }

}
