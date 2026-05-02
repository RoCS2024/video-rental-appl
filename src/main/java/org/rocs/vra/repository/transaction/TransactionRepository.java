package org.rocs.vra.repository.transaction;

import org.rocs.vra.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findTransactionsByCustomer_Id(Long id);
}
