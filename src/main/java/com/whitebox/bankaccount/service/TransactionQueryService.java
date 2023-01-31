package com.whitebox.bankaccount.service;

import com.whitebox.bankaccount.dto.AccountTransactionsDTO;
import com.whitebox.bankaccount.query.FindAccountTransactionsQuery;
import com.whitebox.bankaccount.query.FindAccountTransactionsSinceQuery;
import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public CompletableFuture<AccountTransactionsDTO> findAllSince(String accountId, Date since) {
        return this.queryGateway.query(
                new FindAccountTransactionsSinceQuery(UUID.fromString(accountId), since),
                ResponseTypes.instanceOf(AccountTransactionsDTO.class)
        );
    }
}