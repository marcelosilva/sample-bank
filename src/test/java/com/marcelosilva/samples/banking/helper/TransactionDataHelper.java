package com.marcelosilva.samples.banking.helper;

import com.marcelosilva.samples.banking.enums.TransactionType;
import com.marcelosilva.samples.banking.model.Transaction;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionDataHelper {

    public Transaction getDepositTransaction() {
        return Transaction.builder()
                .transactionType(TransactionType.DEPOSIT)
                .executedAt(new Date())
                .id(1L)
                .value(BigDecimal.TEN).build();
    }

    public Transaction getWithDrawTransaction() {
        return Transaction.builder()
                .transactionType(TransactionType.WITHDRAW)
                .executedAt(new Date())
                .id(2L)
                .value(BigDecimal.TEN).build();
    }

}
