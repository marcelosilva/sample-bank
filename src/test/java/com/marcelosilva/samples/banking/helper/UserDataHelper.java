package com.marcelosilva.samples.banking.helper;

import com.marcelosilva.samples.banking.model.Account;
import com.marcelosilva.samples.banking.model.User;

import java.math.BigDecimal;
import java.util.Arrays;

public class UserDataHelper {

    public User getUser() {
        return User.builder().id(1L).email("email@email.com").password("test").build();
    }

    public User getUserWithAccount() {
        return User.builder().id(1L).email("email@email.com")
                .password("test")
                .accounts(Arrays.asList(Account.builder()
                        .id(1L)
                        .balance(BigDecimal.ZERO)
                        .build()))
                .build();
    }

}
