package com.marcelosilva.samples.banking.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcelosilva.samples.banking.exception.BusinessException;
import com.marcelosilva.samples.banking.helper.UserDataHelper;
import com.marcelosilva.samples.banking.service.UserService;
import com.marcelosilva.samples.banking.web.assembler.AccountAssembler;
import com.marcelosilva.samples.banking.web.assembler.UserAccountAssembler;
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

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    private UserDataHelper userDataHelper = new UserDataHelper();

    @MockBean
    private UserService userService;

    @TestConfiguration
    static class UserControllerTestContextConfiguration {
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
    public void setUp() throws BusinessException {
        Mockito.when(userService.create(Mockito.any())).thenReturn(userDataHelper.getUser());
        Mockito.when(userService.createWithAccount(Mockito.any())).thenReturn(userDataHelper.getUserWithAccount());
    }

    @Test
    public void creatingUser_shouldReturnUserWebModel() throws Exception {
        mvc.perform(post("/users")
                .content(new ObjectMapper().writeValueAsString(userDataHelper.getUserWithAccount()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(userDataHelper.getUserWithAccount().getEmail())));
    }

}
