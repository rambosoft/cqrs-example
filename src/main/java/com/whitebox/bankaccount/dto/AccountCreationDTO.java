package com.whitebox.bankaccount.dto;

import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;

@Data
public class AccountCreationDTO {
    private final BigDecimal initialBalance;
    private final String owner;
}
