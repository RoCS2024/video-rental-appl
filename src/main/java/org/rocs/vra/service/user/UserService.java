package org.rocs.vra.service.user;

import org.rocs.vra.domain.user.User;
import org.rocs.vra.exception.domain.EmailExistsException;
import org.rocs.vra.exception.domain.EmailNotFoundException;
import org.rocs.vra.exception.domain.UsernameExistsException;
import jakarta.mail.MessagingException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * Service interface defining operations for user management.
 */
public interface UserService {

    /**
     * Registers a new user in the system.
     */
    User register(User user) throws UsernameNotFoundException, UsernameExistsException, EmailExistsException, MessagingException;

    /**
     * Retrieves all registered users.
     */
    List<User> getUsers();

    /**
     * Finds a user by their unique username.
     */
    User findUserByUsername(String username);

    /**
     * Finds a user by their email address.
     */
    User findUserByEmail(String email);

    /**
     * Initiates a password reset process for the user identified by the given email.
     */
    void resetPassword(String email) throws EmailNotFoundException, MessagingException;

    /**
     * Deletes a user by their unique identifier.
     */
    void deleteUser(Long id);
}
