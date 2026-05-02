package org.rocs.vra.service.transaction;

import org.rocs.vra.domain.transaction.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByCustomerId(Long customerId);
    void rent(Transaction transaction);
}
