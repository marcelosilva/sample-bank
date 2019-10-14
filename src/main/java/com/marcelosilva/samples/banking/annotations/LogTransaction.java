package com.marcelosilva.samples.banking.annotations;

import com.marcelosilva.samples.banking.enums.TransactionType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LogTransaction {
    TransactionType transactionType();
}