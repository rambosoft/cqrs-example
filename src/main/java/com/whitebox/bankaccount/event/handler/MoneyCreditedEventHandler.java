package com.whitebox.bankaccount.event.handler;

import com.whitebox.bankaccount.enums.TransactionStatus;
import com.whitebox.bankaccount.enums.TransactionType;
import com.whitebox.bankaccount.repository.BankTransactionRepository;
import com.whitebox.bankaccount.entity.BankAccount;
import com.whitebox.bankaccount.event.MoneyCreditedEvent;
import com.whitebox.bankaccount.repository.BankAccountRepository;
import com.whitebox.bankaccount.entity.BankTransaction;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.Optional;


@Component
@AllArgsConstructor
public class MoneyCreditedEventHandler {
    private final BankAccountRepository accountRepository;
    private final BankTransactionRepository repository;

    @EventHandler
    public void on(MoneyCreditedEvent event) throws EntityNotFoundException {
        Optional<BankAccount> optionalBankAccount = this.accountRepository.findById(event.getId());

        if (optionalBankAccount.isPresent()) {
            BankAccount bankAccount = optionalBankAccount.get();
            bankAccount.setBalance(bankAccount.getBalance().add(event.getCreditAmount()));
            this.accountRepository.save(bankAccount);

            BankTransaction bankTransaction = BankTransaction.builder()
                    .accountId(bankAccount.getId())
                    .amount(event.getCreditAmount())
                    .type(TransactionType.CREDIT)
                    .status(TransactionStatus.DONE)
                    .date(Instant.now())
                    .build();

            this.repository.save(bankTransaction);
        } else {
            throw new EntityNotFoundException("Account not found");
        }
    }
}
