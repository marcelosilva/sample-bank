package com.marcelosilva.samples.banking.service;

import com.marcelosilva.samples.banking.exception.BusinessException;
import com.marcelosilva.samples.banking.model.User;

import java.util.Optional;

public interface UserService {
    User create(User user) throws BusinessException;

    User createWithAccount(User user) throws BusinessException;

    Optional<User> authenticate(String email, String password);
}
