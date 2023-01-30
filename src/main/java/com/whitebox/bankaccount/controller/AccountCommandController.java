package com.whitebox.bankaccount.controller;

import com.whitebox.bankaccount.dto.AccountCreationDTO;
import com.whitebox.bankaccount.service.AccountCommandService;
import com.whitebox.bankaccount.entity.BankAccount;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/account")
@AllArgsConstructor
public class AccountCommandController {
    private final AccountCommandService accountCommandService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CompletableFuture<BankAccount> createAccount(@RequestBody AccountCreationDTO creationDTO) {
        return this.accountCommandService.createAccount(creationDTO);
    }
}