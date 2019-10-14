package com.marcelosilva.samples.banking.service;

import com.marcelosilva.samples.banking.model.Account;
import com.marcelosilva.samples.banking.model.User;

import java.util.List;
import java.util.Optional;

public interface AccountDataService {

    Optional<Account> get(Long accountId);

    List<Account> find(User user);

    Account createByUser(User user);

}
