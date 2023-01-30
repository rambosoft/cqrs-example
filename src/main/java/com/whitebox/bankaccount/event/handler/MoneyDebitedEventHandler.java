package com.whitebox.bankaccount.event.handler;

import com.whitebox.bankaccount.enums.TransactionStatus;
import com.whitebox.bankaccount.enums.TransactionType;
import com.whitebox.bankaccount.repository.BankTransactionRepository;
import com.whitebox.bankaccount.entity.BankAccount;
import com.whitebox.bankaccount.event.MoneyDebitedEvent;
import com.whitebox.bankaccount.repository.BankAccountRepository;
import com.whitebox.bankaccount.entity.BankTransaction;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;


@Component
@AllArgsConstructor
public class MoneyDebitedEventHandler {
    private final BankAccountRepository accountRepository;
    private final BankTransactionRepository repository;

    @EventHandler
    public void on(MoneyDebitedEvent event) throws EntityNotFoundException {
        Optional<BankAccount> optionalBankAccount = this.accountRepository.findById(event.getId());


        if (optionalBankAccount.isPresent()) {
            BankAccount bankAccount = optionalBankAccount.get();
            BigDecimal newBalance = bankAccount.getBalance().subtract(event.getDebitAmount());

            bankAccount.setBalance(newBalance);
            this.accountRepository.save(bankAccount);

            BankTransaction bankTransaction = BankTransaction.builder()
                    .accountId(bankAccount.getId())
                    .amount(event.getDebitAmount())
                    .type(TransactionType.DEBIT)
                    .status(newBalance.compareTo(BigDecimal.ZERO) < 0? TransactionStatus.OUTBOUND :TransactionStatus.DONE)
                    .date(Instant.now())
                    .build();

            this.repository.save(bankTransaction);
        } else {
            throw new EntityNotFoundException("Account not found");
        }
    }
}
