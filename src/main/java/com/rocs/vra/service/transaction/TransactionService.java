package com.rocs.vra.service.transaction;

import com.rocs.vra.domain.transaction.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByCustomerId(String customerId);
    void rent(Transaction transaction);
}
