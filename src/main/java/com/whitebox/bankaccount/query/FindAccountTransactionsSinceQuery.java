package com.whitebox.bankaccount.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindAccountTransactionsSinceQuery {
    private UUID accountId;
    private Date since;
}
