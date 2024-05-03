package com.rocs.vra.repository.user;

import com.rocs.vra.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByCustomerEmail(String email);
}
