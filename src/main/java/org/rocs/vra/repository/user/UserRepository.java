package org.rocs.vra.repository.user;

import org.rocs.vra.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations and custom queries on User entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves a user by their unique username.
     */
    User findUserByUsername(String username);

    /**
     * Retrieves a user by the email address of the associated customer.
     */
    User findUserByCustomerEmail(String email);
}
