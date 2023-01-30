package com.whitebox.bankaccount.service;

import com.whitebox.bankaccount.command.CreateAccountCommand;
import com.whitebox.bankaccount.dto.AccountCreationDTO;
import com.whitebox.bankaccount.entity.BankAccount;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class AccountCommandService {
    private final CommandGateway commandGateway;

    public CompletableFuture<BankAccount> createAccount(AccountCreationDTO creationDTO) {
        return this.commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID(),
                creationDTO.getInitialBalance(),
                creationDTO.getOwner()
        ));
    }
}