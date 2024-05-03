package com.rocs.vra.service.user.impl;

import com.rocs.vra.domain.user.User;
import com.rocs.vra.domain.user.principal.UserPrincipal;
import com.rocs.vra.exception.domain.EmailExistsException;
import com.rocs.vra.exception.domain.EmailNotFoundException;
import com.rocs.vra.exception.domain.UsernameExistsException;
import com.rocs.vra.repository.customer.CustomerRepository;
import com.rocs.vra.repository.user.UserRepository;
import com.rocs.vra.service.email.EmailService;
import com.rocs.vra.service.login.attempt.LoginAttemptService;
import com.rocs.vra.service.user.UserService;
import com.rocs.vra.utils.security.enumeration.Role;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.rocs.vra.utils.security.enumeration.Role.ROLE_USER;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private UserRepository userRepository;
    private CustomerRepository customerRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private LoginAttemptService loginAttemptService;

    private EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           CustomerRepository customerRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           LoginAttemptService loginAttemptService,
                           EmailService emailService) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.loginAttemptService = loginAttemptService;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findUserByUsername(username);
        if(user == null) {
            LOGGER.error("User not found...");
            throw new UsernameNotFoundException("User not found...");
        } else {
            validateLoginAttempt(user);
            user.setLastLoginDate(new Date());
            this.userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            LOGGER.error("User information found...");
            return userPrincipal;
        }
    }

    private void validateLoginAttempt(User user) {
        if(!user.isLocked()) {
            if(loginAttemptService.hasExceededMaxAttempts(user.getUsername())) {
                user.setLocked(true);
            } else {
                user.setLocked(false);
            }
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }

    @Override
    public User register(User newUser)
            throws UsernameNotFoundException, UsernameExistsException, EmailExistsException, MessagingException {
        validateNewUsernameAndEmail(StringUtils.EMPTY, newUser.getUsername(), newUser.getCustomer().getEmail());
        User user = new User();
        user.setUserId(generateUserId());
        String password = generatePassword();
        String encodedPassword = encodePassword(password);
        user.setCustomer(newUser.getCustomer());
        user.setJoinDate(new Date());
        user.setUsername(newUser.getUsername());
        user.setPassword(encodedPassword);
        user.setActive(true);
        user.setLocked(false);
        user.setRole(Role.ROLE_USER.name());
        user.setAuthorities(Arrays.stream(ROLE_USER.getAuthorities()).toList());
        userRepository.save(user);
        emailService.sendNewPasswordEmail(newUser.getCustomer().getFirstName(), password, newUser.getCustomer().getEmail());
        return null;
    }

    private String generateUserId() {
        return RandomStringUtils.randomNumeric(10);
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String email)
            throws UsernameNotFoundException, UsernameExistsException, EmailExistsException {
        User userByNewUsername = findUserByUsername(newUsername);
        User userByNewEmail = findUserByEmail(email);
        if(StringUtils.isNotBlank(currentUsername)) {
            User currentUser = findUserByUsername(currentUsername);
            if(currentUser == null) {
                throw new UsernameNotFoundException("User not found.");
            }
            if(userByNewUsername != null && !userByNewUsername.getId().equals(currentUser.getId())) {
                throw new UsernameExistsException("Username already exists. ");
            }
            if(userByNewEmail != null && !userByNewEmail.getId().equals(currentUser.getId())) {
                throw new EmailExistsException("Email already exists. ");
            }
            return currentUser;
        } else {
            if(userByNewUsername != null) {
                throw new UsernameExistsException("Username already exists. ");
            }
            if(userByNewEmail != null) {
                throw new EmailExistsException("Email already exists. ");
            }
            return null;
        }
    }

    @Override
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User findUserByUsername(String username) {
        return this.userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return this.userRepository.findUserByCustomerEmail(email);
    }

    @Override
    public void resetPassword(String email) throws EmailNotFoundException, MessagingException {
        User user = findUserByEmail(email);
        if(user == null) {
            throw new EmailNotFoundException("User with email " + email + " not found.");
        }
        String password = generatePassword();
        user.setPassword(encodePassword(password));
        userRepository.save(user);
        emailService.sendNewPasswordEmail(user.getCustomer().getFirstName(), password, user.getCustomer().getEmail());
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
