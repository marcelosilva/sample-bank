package com.marcelosilva.samples.banking.web;

import com.marcelosilva.samples.banking.helper.TransactionDataHelper;
import com.marcelosilva.samples.banking.model.Transaction;
import com.marcelosilva.samples.banking.service.TransactionService;
import com.marcelosilva.samples.banking.web.assembler.TransactionAssembler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mvc;

    private TransactionDataHelper transactionDataHelper = new TransactionDataHelper();

    @MockBean
    private TransactionService transactionService;

    @TestConfiguration
    static class TransactionControllerTestContextConfiguration {
        @Bean
        public TransactionAssembler transactionAssembler() {
            return new TransactionAssembler();
        }
    }

    @Before
    public void setUp() {

        List<Transaction> transactions = Arrays.asList(transactionDataHelper.getDepositTransaction(),
                transactionDataHelper.getWithDrawTransaction());

        Mockito.when(transactionService.findByAccount(Mockito.anyLong()))
                .thenReturn(transactions);

    }

    @Test
    public void findingTransactions_shouldReturnListOfTransactionWebModel() throws Exception {
        mvc.perform(get("/accounts/1/transactions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

}
