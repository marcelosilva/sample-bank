package com.marcelosilva.samples.banking.service.impl;

import com.marcelosilva.samples.banking.utils.CryptoUtils;
import com.marcelosilva.samples.banking.dao.UserDAO;
import com.marcelosilva.samples.banking.exception.BusinessException;
import com.marcelosilva.samples.banking.model.Account;
import com.marcelosilva.samples.banking.model.User;
import com.marcelosilva.samples.banking.service.AccountDataService;
import com.marcelosilva.samples.banking.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private AccountDataService accountDataService;
    private UserDAO userDAO;

    public UserServiceImpl(AccountDataService accountDataService, UserDAO userDAO) {
        this.accountDataService = accountDataService;
        this.userDAO = userDAO;
    }

    @Override
    public User create(User user) throws BusinessException {

        if (this.userDAO.findByEmail(user.getEmail()).isPresent()) {
            throw new BusinessException("User already exists");
        }

        user.setPassword(CryptoUtils.getMD5(user.getPassword()));
        return userDAO.save(user);

    }

    @Override
    public User createWithAccount(User user) throws BusinessException {
        User newUser = this.create(user);
        Account newAccount = accountDataService.createByUser(newUser);
        newUser.setAccounts(Arrays.asList(newAccount));
        return newUser;
    }

    @Override
    public Optional<User> authenticate(String email, String password) {
        return userDAO.findByEmailAndPassword(email, CryptoUtils.getMD5(password));
    }
}
