package com.rocs.vra.service.transaction.impl;

import com.rocs.vra.domain.transaction.Transaction;
import com.rocs.vra.repository.transaction.TransactionRepository;
import com.rocs.vra.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getTransactionsByCustomerId(String customerId) {
        return transactionRepository.findTransactionsByCustomer_Id(customerId);
    }

    @Override
    public void rent(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
