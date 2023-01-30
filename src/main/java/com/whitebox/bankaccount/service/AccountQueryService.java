package com.whitebox.bankaccount.service;

import com.whitebox.bankaccount.dto.RedAccountsDTO;
import com.whitebox.bankaccount.entity.BankAccount;
import com.whitebox.bankaccount.query.FindBankAccountQuery;
import com.whitebox.bankaccount.query.FindRedBankAccountsQuery;
import lombok.AllArgsConstructor;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountQueryService {
    private final QueryGateway queryGateway;
    private final EventStore eventStore;

    public CompletableFuture<BankAccount> findById(String accountId) {
        return this.queryGateway.query(
                new FindBankAccountQuery(UUID.fromString(accountId)),
                ResponseTypes.instanceOf(BankAccount.class)
        );
    }

    public CompletableFuture<RedAccountsDTO> findAllRedAccounts() {
        return this.queryGateway.query(
                new FindRedBankAccountsQuery(BigDecimal.ZERO),
                ResponseTypes.instanceOf(RedAccountsDTO.class)
        );
    }

    public List<Object> listEventsForAccount(String accountId) {
        return this.eventStore
                .readEvents(UUID.fromString(accountId).toString())
                .asStream()
                .map(Message::getPayload)
                .collect(Collectors.toList());
    }
}