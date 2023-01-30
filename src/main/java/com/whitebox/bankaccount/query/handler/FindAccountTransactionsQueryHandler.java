package com.whitebox.bankaccount.query.handler;

import com.whitebox.bankaccount.dto.AccountTransactionsDTO;
import com.whitebox.bankaccount.repository.BankTransactionRepository;
import com.whitebox.bankaccount.query.FindAccountTransactionsQuery;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FindAccountTransactionsQueryHandler {

    private final BankTransactionRepository repository;

    @QueryHandler
    public AccountTransactionsDTO handle(FindAccountTransactionsQuery query) {
        return AccountTransactionsDTO.builder()
                .transactions(this.repository.findAllByAccountIdOrderByDateDesc(query.getAccountId()))
                .build();
    }
}