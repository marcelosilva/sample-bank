package com.marcelosilva.samples.banking.web;

import com.marcelosilva.samples.banking.exception.NoFundsException;
import com.marcelosilva.samples.banking.helper.UserDataHelper;
import com.marcelosilva.samples.banking.service.UserService;
import com.marcelosilva.samples.banking.web.assembler.AccountAssembler;
import com.marcelosilva.samples.banking.web.assembler.UserAccountAssembler;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private UserDataHelper userDataHelper = new UserDataHelper();

    @TestConfiguration
    static class AuthenticationControllerTestContextConfiguration {
        @Bean
        public UserAccountAssembler userAssembler() {
            return new UserAccountAssembler();
        }

        @Bean
        public AccountAssembler accountAssembler() {
            return new AccountAssembler();
        }

    }

    @Before
    public void setUp() throws NoFundsException {
        Mockito.when(userService.authenticate(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of(userDataHelper.getUser()));
    }

    @Test
    public void whenAuthenticating_shouldReturnUserWebModel() throws Exception {
        mvc.perform(post("/auth").param("email", userDataHelper.getUser().getEmail())
                .param("password", "test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", new StringContains(userDataHelper.getUser().getEmail())));
    }

}
