package com.marcelosilva.samples.banking.helper;

import com.marcelosilva.samples.banking.model.Account;

import java.math.BigDecimal;
import java.util.ArrayList;

public class AccountDataHelper {

    public Account getAccount() {
        return Account.builder().balance(BigDecimal.ZERO)
                .id(1L).transactions(new ArrayList<>())
                .build();
    }

    public Account getAccountWithBalance(BigDecimal balance) {
        return Account.builder().balance(balance)
                .id(1L).transactions(new ArrayList<>())
                .build();
    }

}
