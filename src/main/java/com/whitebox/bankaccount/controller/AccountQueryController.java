package com.whitebox.bankaccount.controller;

import com.whitebox.bankaccount.dto.AccountTransactionsDTO;
import com.whitebox.bankaccount.dto.RedAccountsDTO;
import com.whitebox.bankaccount.service.TransactionQueryService;
import com.whitebox.bankaccount.entity.BankAccount;
import com.whitebox.bankaccount.service.AccountQueryService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/account")
public class AccountQueryController {

    private final AccountQueryService accountQueryService;
    private final TransactionQueryService transactionQueryService;

    @GetMapping("/{accountId}")
    public BankAccount findById(@PathVariable("accountId") String accountId) throws ExecutionException, InterruptedException {
        return this.accountQueryService.findById(accountId).get();
    }

    @GetMapping("/red")
    public CompletableFuture<RedAccountsDTO> findAllRedAccounts() {
        return this.accountQueryService.findAllRedAccounts();
    }

    @GetMapping("/{accountId}/events")
    public List<Object> listEventsForAccount(@PathVariable(value = "accountId") String accountId) {
        return this.accountQueryService.listEventsForAccount(accountId);
    }

    @GetMapping("/{accountId}/transactions/all")
    public CompletableFuture<AccountTransactionsDTO> findAllTransactions(@PathVariable("accountId") String accountId) {
        return this.transactionQueryService.findAll(accountId);
    }

    @GetMapping("/{accountId}/transactions")
    public CompletableFuture<AccountTransactionsDTO> findAllTransactionsSince(@PathVariable("accountId") String accountId,
                                                                         @RequestParam("since") @DateTimeFormat(pattern="yyyy-MM-dd") Date sinceDate) {
        return this.transactionQueryService.findAllSince(accountId, sinceDate);
    }
}