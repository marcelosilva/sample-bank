package com.marcelosilva.samples.banking.service.impl;

import com.marcelosilva.samples.banking.dao.TransactionDAO;
import com.marcelosilva.samples.banking.helper.TransactionDataHelper;
import com.marcelosilva.samples.banking.model.Transaction;
import com.marcelosilva.samples.banking.service.TransactionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
public class TransactionServiceImplTest {

    private TransactionService transactionService;

    @MockBean
    private TransactionDAO transactionDAO;

    @Before
    public void setUp() {

        TransactionDataHelper transactionDataHelper = new TransactionDataHelper();

        Mockito.when(transactionDAO.findByAccount_Id(Mockito.anyLong()))
                .thenReturn(Arrays.asList(transactionDataHelper.getDepositTransaction(),
                        transactionDataHelper.getWithDrawTransaction()));

        transactionService = new TransactionServiceImpl(transactionDAO);
    }

    @Test
    public void whenFindTransactions_ThenList() {
        List<Transaction> byAccount = transactionService.findByAccount(1L);
        Assert.assertEquals(2, byAccount.size());
    }

}
