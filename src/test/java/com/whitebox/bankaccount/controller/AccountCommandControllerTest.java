package com.whitebox.bankaccount.controller;

import com.whitebox.bankaccount.AbstractIntegrationTest;
import com.whitebox.bankaccount.dto.AccountCreationDTO;
import com.whitebox.bankaccount.repository.BankAccountRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountCommandControllerTest extends AbstractIntegrationTest {

    @Autowired
    private BankAccountRepository repository;

    @MockBean
    private CommandGateway commandGateway;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("POST /account Should return a response of the created account with CREATED status")
    public void shouldReturnCreatedAccount() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/account")
                        .content(asJsonString(new AccountCreationDTO(BigDecimal.valueOf(100), RandomStringUtils.random(10))))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        verify(commandGateway, times(1)).send(any());
    }

}
