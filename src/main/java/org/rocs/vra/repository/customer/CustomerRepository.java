package org.rocs.vra.repository.customer;

import org.rocs.vra.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations and custom queries on Customer entities.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
