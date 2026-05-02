package org.rocs.vra.repository.transaction;

import org.rocs.vra.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations and custom queries on Transaction entities.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findTransactionsByCustomer_Id(Long id);
}
