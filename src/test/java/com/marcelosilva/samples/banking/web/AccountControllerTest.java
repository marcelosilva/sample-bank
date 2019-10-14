package com.marcelosilva.samples.banking.web;

import com.marcelosilva.samples.banking.exception.BusinessException;
import com.marcelosilva.samples.banking.exception.NoFundsException;
import com.marcelosilva.samples.banking.helper.AccountDataHelper;
import com.marcelosilva.samples.banking.service.AccountDataService;
import com.marcelosilva.samples.banking.service.AccountOperationService;
import com.marcelosilva.samples.banking.web.assembler.AccountAssembler;
import org.hamcrest.core.StringContains;
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

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    public static final String ACCOUNT_LINK = "/accounts/1";
    @Autowired
    private MockMvc mvc;

    private AccountDataHelper accountDataHelper = new AccountDataHelper();

    @MockBean
    private AccountDataService accountDataService;

    @MockBean
    AccountOperationService accountOperationService;

    @TestConfiguration
    static class AccountControllerTestContextConfiguration {
        @Bean
        public AccountAssembler accountAssembler() {
            return new AccountAssembler();
        }
    }

    @Before
    public void setUp() throws NoFundsException, BusinessException {
        Mockito.when(accountDataService.get(Mockito.any())).thenReturn(Optional.of(accountDataHelper.getAccount()));
        Mockito.when(accountOperationService.withdraw(Mockito.anyLong(), Mockito.any()))
                .thenReturn(accountDataHelper.getAccount());
        Mockito.when(accountOperationService.deposit(Mockito.anyLong(), Mockito.any()))
                .thenReturn(accountDataHelper.getAccount());

    }

    @Test
    public void creatingAccount_shouldReturnAccountWebModel() throws Exception {
        mvc.perform(get(ACCOUNT_LINK)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href", new StringContains(ACCOUNT_LINK)));
    }

    @Test
    public void whenWithDraw_shouldReturnAccountWebModel() throws Exception {
        mvc.perform(post("/accounts/1/withdraw").param("amount", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href", new StringContains(ACCOUNT_LINK)));
    }

    @Test
    public void whenDepositing_shouldReturnAccountWebModel() throws Exception {
        mvc.perform(post("/accounts/1/deposit").param("amount", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href", new StringContains(ACCOUNT_LINK)));
    }


}
