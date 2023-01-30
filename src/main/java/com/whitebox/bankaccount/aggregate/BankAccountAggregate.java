package com.whitebox.bankaccount.aggregate;

import com.whitebox.bankaccount.command.CreateAccountCommand;
import com.whitebox.bankaccount.command.CreditMoneyCommand;
import com.whitebox.bankaccount.command.MoneyDebitCommand;
import com.whitebox.bankaccount.event.AccountCreatedEvent;
import com.whitebox.bankaccount.event.MoneyCreditedEvent;
import com.whitebox.bankaccount.event.MoneyDebitedEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Aggregate
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountAggregate {

    @AggregateIdentifier
    private UUID id;
    private BigDecimal balance;
    private String owner;

    @CommandHandler
    public BankAccountAggregate(CreateAccountCommand command) {
        AggregateLifecycle.apply(
                new AccountCreatedEvent(
                        command.getAccountId(),
                        command.getInitialBalance(),
                        command.getOwner()
                )
        );
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.id = event.getId();
        this.owner = event.getOwner();
        this.balance = event.getInitialBalance();
    }

    @CommandHandler
    public UUID handel(CreditMoneyCommand command) {
        AggregateLifecycle.apply(
                new MoneyCreditedEvent(
                        command.getAccountId(),
                        command.getCreditAmount()
                )
        );
        return command.getAccountId();
    }

    @EventSourcingHandler
    public void on(MoneyCreditedEvent event) {
        this.balance = this.balance.add(event.getCreditAmount());
    }

    @CommandHandler
    public UUID handel(MoneyDebitCommand command) {
        AggregateLifecycle.apply(
                new MoneyDebitedEvent(
                        command.getAccountId(),
                        command.getDebitAmount()
                )
        );

        return command.getAccountId();
    }

//    @EventSourcingHandler
//    public void on(MoneyDebitedEvent event) throws InsufficientBalanceException {
//        if (this.balance.compareTo(event.getDebitAmount()) < 0) {
//            throw new InsufficientBalanceException(event.getId(), event.getDebitAmount());
//        }
    @EventSourcingHandler
    public void on(MoneyDebitedEvent event) {
        this.balance = this.balance.subtract(event.getDebitAmount());
    }
}