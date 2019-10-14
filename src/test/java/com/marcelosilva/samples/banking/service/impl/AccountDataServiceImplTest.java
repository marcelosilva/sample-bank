package com.marcelosilva.samples.banking.service.impl;

import com.marcelosilva.samples.banking.dao.AccountDAO;
import com.marcelosilva.samples.banking.helper.AccountDataHelper;
import com.marcelosilva.samples.banking.helper.UserDataHelper;
import com.marcelosilva.samples.banking.model.Account;
import com.marcelosilva.samples.banking.service.AccountDataService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class AccountDataServiceImplTest {

    private AccountDataService accountDataService;

    @MockBean
    private AccountDAO accountDAO;

    @Before
    public void setUp() {

        Mockito.when(accountDAO.findByUser_Id(Mockito.anyLong()))
                .thenReturn(Arrays.asList(new AccountDataHelper().getAccount(),
                        new AccountDataHelper().getAccount()));

        Mockito.when(accountDAO.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(new AccountDataHelper().getAccount()));

        Mockito.when(accountDAO.save(Mockito.any()))
                .thenReturn(new AccountDataHelper().getAccount());

        accountDataService = new AccountDataServiceImpl(accountDAO);
    }


    @Test
    public void whenFindAccount_thenReturn() {
        Optional<Account> account = accountDataService.get(1L);
        Assert.assertTrue(account.isPresent());

        if (account.isPresent()) {
            Account accountFound = account.get();
            Assert.assertEquals(Long.valueOf(1L), accountFound.getId());
        }

    }

    @Test
    public void whenFindAccountsByUser_thenReturn() {
        List<Account> accounts = accountDataService.find(new UserDataHelper().getUser());
        Assert.assertEquals(2, accounts.size());
    }

    @Test
    public void whenCreatingAccount_shouldReturnAccount() {
        Account account = accountDataService.createByUser(new UserDataHelper().getUser());
        Assert.assertEquals(account.getId(), new AccountDataHelper().getAccount().getId());
    }

}
