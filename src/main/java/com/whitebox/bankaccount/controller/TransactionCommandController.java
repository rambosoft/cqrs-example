package com.whitebox.bankaccount.controller;

import com.whitebox.bankaccount.dto.MoneyAmountDTO;
import com.whitebox.bankaccount.service.TransactionCommandService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "transaction")
@AllArgsConstructor
public class TransactionCommandController {
    private final TransactionCommandService transactionCommandService;


    @PutMapping(value = "/credit/{accountId}")
    public CompletableFuture<String> creditMoneyToAccount(@PathVariable(value = "accountId") String accountId,
                                                          @RequestBody MoneyAmountDTO moneyCreditDTO) {
        return this.transactionCommandService.creditMoneyToAccount(accountId, moneyCreditDTO);
    }

    @PutMapping(value = "/debit/{accountId}")
    public CompletableFuture<String> debitMoneyFromAccount(@PathVariable(value = "accountId") String accountId,
                                                           @RequestBody MoneyAmountDTO moneyDebitDTO) {
        return this.transactionCommandService.debitMoneyFromAccount(accountId, moneyDebitDTO);
    }
}