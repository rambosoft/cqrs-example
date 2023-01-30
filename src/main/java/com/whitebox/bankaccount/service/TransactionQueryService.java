package com.whitebox.bankaccount.service;

import com.whitebox.bankaccount.dto.AccountTransactionsDTO;
import com.whitebox.bankaccount.query.FindAccountTransactionsQuery;
import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class TransactionQueryService {
    private QueryGateway queryGateway;

    public CompletableFuture<AccountTransactionsDTO> findAll(String accountId) {
        return this.queryGateway.query(
                new FindAccountTransactionsQuery(UUID.fromString(accountId)),
                ResponseTypes.instanceOf(AccountTransactionsDTO.class)
        );
    }
}