package com.whitebox.bankaccount.query.handler;

import com.whitebox.bankaccount.repository.BankAccountRepository;
import com.whitebox.bankaccount.entity.BankAccount;
import com.whitebox.bankaccount.query.FindBankAccountQuery;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FindBankAccountQueryHandler {

    private final BankAccountRepository repository;

    @QueryHandler
    public BankAccount handle(FindBankAccountQuery query) {
        return this.repository.findById(query.getAccountId()).orElse(null);
    }
}