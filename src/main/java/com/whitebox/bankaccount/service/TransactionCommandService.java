package com.whitebox.bankaccount.service;

import com.whitebox.bankaccount.command.CreditMoneyCommand;
import com.whitebox.bankaccount.command.MoneyDebitCommand;
import com.whitebox.bankaccount.dto.MoneyAmountDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class TransactionCommandService {
    private final CommandGateway commandGateway;

    public CompletableFuture<String> creditMoneyToAccount(String accountId,
                                                          MoneyAmountDTO moneyCreditDTO) {
        return this.commandGateway.send(new CreditMoneyCommand(
                UUID.fromString(accountId),
                moneyCreditDTO.getAmount()));
    }

    public CompletableFuture<String> debitMoneyFromAccount(String accountId,
                                                           MoneyAmountDTO moneyDebitDTO) {
        return this.commandGateway.send(new MoneyDebitCommand(
                UUID.fromString(accountId),
                moneyDebitDTO.getAmount()
        ));
    }
}