package com.marcelosilva.samples.banking.service.impl;

import com.marcelosilva.samples.banking.dao.AccountDAO;
import com.marcelosilva.samples.banking.exception.BusinessException;
import com.marcelosilva.samples.banking.exception.NoFundsException;
import com.marcelosilva.samples.banking.helper.AccountDataHelper;
import com.marcelosilva.samples.banking.model.Account;
import com.marcelosilva.samples.banking.service.AccountOperationService;
import com.marcelosilva.samples.banking.validator.impl.PositiveAmountValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class AccountOperationServiceImplTest {

    private AccountOperationService accountOperationService;

    @MockBean
    private AccountDAO accountDAO;

    @Before
    public void setUp() {

        Mockito.when(accountDAO.findAndLockById(Mockito.anyLong()))
                .thenReturn(Optional.of(new AccountDataHelper().getAccountWithBalance(BigDecimal.TEN)));

        Mockito.when(accountDAO.save(Mockito.any()))
                .thenReturn(new AccountDataHelper().getAccount());

        accountOperationService = new AccountOperationServiceImpl(accountDAO, new PositiveAmountValidator());
    }

    @Test
    public void whenDepositing_shouldIncrementAccountBalance() throws BusinessException {
        Account account = accountOperationService.deposit(1L, BigDecimal.TEN);
        Assert.assertEquals(new BigDecimal("20"), account.getBalance());
    }

    @Test
    public void whenWithDraw_shouldReduceAccountBalance() throws NoFundsException, BusinessException {
        Account account = accountOperationService.withdraw(1L, BigDecimal.TEN);
        Assert.assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test(expected = NoFundsException.class)
    public void whenWithDraw_shouldNotAllow() throws NoFundsException, BusinessException {
        accountOperationService.withdraw(1L, new BigDecimal("100"));
    }

}
