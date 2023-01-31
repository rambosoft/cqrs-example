package com.whitebox.bankaccount.query.handler;

import com.whitebox.bankaccount.dto.AccountTransactionsDTO;
import com.whitebox.bankaccount.query.FindAccountTransactionsSinceQuery;
import com.whitebox.bankaccount.repository.BankTransactionRepository;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FindAccountTransactionsSinceQueryHandler {

    private final BankTransactionRepository repository;

    @QueryHandler
    public AccountTransactionsDTO handle(FindAccountTransactionsSinceQuery query) {
        return AccountTransactionsDTO.builder()
                .transactions(this.repository.findAllByAccountIdAndDateAfterOrderByDateDesc(query.getAccountId(), query.getSince().toInstant()))
                .build();
    }
}