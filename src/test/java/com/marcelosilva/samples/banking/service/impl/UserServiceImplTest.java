package com.marcelosilva.samples.banking.service.impl;

import com.marcelosilva.samples.banking.dao.UserDAO;
import com.marcelosilva.samples.banking.exception.BusinessException;
import com.marcelosilva.samples.banking.helper.AccountDataHelper;
import com.marcelosilva.samples.banking.helper.UserDataHelper;
import com.marcelosilva.samples.banking.model.User;
import com.marcelosilva.samples.banking.service.AccountDataService;
import com.marcelosilva.samples.banking.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    private UserService userService;

    @MockBean
    private UserDAO userDAO;

    @MockBean
    private AccountDataService accountDataService;

    @Before
    public void setUp() {

        UserDataHelper userDataHelper = new UserDataHelper();

        Mockito.when(accountDataService.createByUser(Mockito.any())).thenReturn(new AccountDataHelper().getAccount());
        Mockito.when(userDAO.save(Mockito.any())).thenReturn(userDataHelper.getUserWithAccount());
        Mockito.when(userDAO.findByEmailAndPassword(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of(userDataHelper.getUserWithAccount()));
        userService = new UserServiceImpl(accountDataService, userDAO);

    }


    @Test
    public void whenCreateUser_shouldReturnNewUser() throws BusinessException {
        User userWithAccount = userService.createWithAccount(new UserDataHelper().getUser());
        Assert.assertNotNull(userWithAccount);
        Assert.assertEquals(userWithAccount.getEmail(), new UserDataHelper().getUser().getEmail());
        Assert.assertEquals(1, userWithAccount.getAccounts().size());
    }

    @Test
    public void whenAuthenticatingUser_shouldReturnUserWithAccuont() throws BusinessException {
        User user = new UserDataHelper().getUser();
        Optional<User> authenticatedUser = userService.authenticate(user.getEmail(), user.getPassword());
        Assert.assertTrue(authenticatedUser.isPresent());
    }

}
