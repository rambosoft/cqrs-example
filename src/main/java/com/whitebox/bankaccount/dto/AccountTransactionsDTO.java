package com.whitebox.bankaccount.dto;

import com.whitebox.bankaccount.entity.BankTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransactionsDTO {
    private List<BankTransaction> transactions;
}