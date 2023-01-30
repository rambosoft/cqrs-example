package com.whitebox.bankaccount.query.handler;

import com.whitebox.bankaccount.dto.RedAccountsDTO;
import com.whitebox.bankaccount.repository.BankAccountRepository;
import com.whitebox.bankaccount.query.FindRedBankAccountsQuery;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FindRedBankAccountsQueryHandler {

    private final BankAccountRepository repository;

    @QueryHandler
    public RedAccountsDTO handle(FindRedBankAccountsQuery query) {
        return RedAccountsDTO.builder()
                .accounts(this.repository.findAllByBalanceLessThan(query.getLimit()))
                .build();
    }
}