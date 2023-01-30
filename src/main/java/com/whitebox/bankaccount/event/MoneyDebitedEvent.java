package com.whitebox.bankaccount.event;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class MoneyDebitedEvent {

    private final UUID id;
    private final BigDecimal debitAmount;
}