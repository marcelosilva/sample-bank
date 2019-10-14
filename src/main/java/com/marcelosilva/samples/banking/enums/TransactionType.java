package com.marcelosilva.samples.banking.enums;

public enum TransactionType {

    WITHDRAW(1),
    DEPOSIT(2);

    private Integer id;


    TransactionType(Integer id) {
        this.id = id;
    }

}
