package com.rocs.vra.domain.user;

import com.rocs.vra.domain.customer.Customer;
import com.rocs.vra.utils.converter.StringListConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "login")
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String userId;
    private String username;
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    private Customer customer;
    private Date lastLoginDate;
    private Date joinDate;
    private String role;

    @Column(name = "authorities", nullable = false)
    @Convert(converter = StringListConverter.class)
    private List<String> authorities;
    private boolean isActive;
    private boolean isLocked;
}
