package org.rocs.vra.domain.user;

import org.rocs.vra.domain.customer.Customer;
import org.rocs.vra.utils.converter.StringListConverter;
import org.rocs.vra.utils.security.enumeration.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Represents a user account entity persisted in the login table.
 * This class stores authentication and authorization information for a system user,
 * including credentials, roles, authorities, and account status flags. It maintains a
 * one-to-one relationship with a Customer entity, linking the user account
 * to a customer's personal details.
 */
@Entity(name = "login")
@Data
public class User implements Serializable {

    /**
     * The unique primary key identifier of the user.
     * This value is automatically generated using the chosen AUTO strategy.
     * The column is non-null and non-updatable.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    /**
     * The public identifier for the user, typically used in APIs or display contexts.
     */
    @Column(nullable = false)
    private String userId;

    /**
     *  The login username of the user.
     */
    @Column(nullable = false)
    private String username;

    /**
     *  The encrypted password of the user.
     */
    @Column(nullable = false)
    private String password;

    /**
     * The associated customer entity.
     * This establishes a one-to-one relationship with Customer where any operations
     * (e.g., persist, merge, remove) are cascaded from the user to the customer.
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Customer customer;

    /**
     * The timestamp of the user's most recent successful login.
     */
    private Date lastLoginDate;

    /**
     * The timestamp when the user account was created (e.g., registration date).
     */
    private Date joinDate;

    /**
     *  The assigned role of the user.
     */
    private Role role;

    /**
     * The list of granular authority strings granted to the user (e.g., "READ", "WRITE", "DELETE").
     * This field is persisted as a single database column using a custom converter
     * StringListConverter that likely transforms the list into a delimited string
     * (e.g., comma-separated) and back.
     */
    @Column(name = "authorities", nullable = false)
    @Convert(converter = StringListConverter.class)
    private List<String> authorities;

    /**
     * Flag indicating whether the user account is active.
     */
    private boolean isActive;

    /**
     * Flag indicating whether the user account is locked.
     */
    private boolean isLocked;
}
