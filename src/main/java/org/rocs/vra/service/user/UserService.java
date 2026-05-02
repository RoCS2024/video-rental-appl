package org.rocs.vra.service.user;

import org.rocs.vra.domain.user.User;
import org.rocs.vra.exception.domain.EmailExistsException;
import org.rocs.vra.exception.domain.EmailNotFoundException;
import org.rocs.vra.exception.domain.UsernameExistsException;
import jakarta.mail.MessagingException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    User register(User user) throws UsernameNotFoundException, UsernameExistsException, EmailExistsException, MessagingException;
    List<User> getUsers();
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    void resetPassword(String email) throws EmailNotFoundException, MessagingException;
    void deleteUser(Long id);
}
