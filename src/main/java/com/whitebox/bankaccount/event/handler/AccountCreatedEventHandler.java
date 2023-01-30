package com.whitebox.bankaccount.event.handler;

import com.whitebox.bankaccount.entity.BankAccount;
import com.whitebox.bankaccount.event.AccountCreatedEvent;
import com.whitebox.bankaccount.repository.BankAccountRepository;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
@AllArgsConstructor
public class AccountCreatedEventHandler {
    private final BankAccountRepository repository;

    @EventHandler
    public void on(AccountCreatedEvent event) {
        BankAccount bankAccount = new BankAccount(
                event.getId(),
                event.getOwner(),
                event.getInitialBalance()
        );

        this.repository.save(bankAccount);
    }
}
