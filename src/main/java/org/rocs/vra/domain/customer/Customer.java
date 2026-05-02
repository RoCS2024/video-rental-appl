package org.rocs.vra.domain.customer;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

/**
 * Represents a customer entity persisted in the database.
 */
@Entity
@Data
public class Customer implements Serializable {

    /**
     * The unique identifier of the customer.
     * This field is the primary key of the entity and
     * is manually assigned rather than generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    /**
     * The first name of the customer.
     * Maximum length is 64 characters.
     * This field is optional.
     */
    @Column(length = 64)
    private String firstName;

    /**
     * The middle name of the customer.
     * Maximum length is 32 characters.
     * This field is optional.
     */
    @Column(length = 32)
    private String middleName;

    /**
     * The last name of the customer.
     * Maximum length is 32 characters.
     * This field is optional.
     */
    @Column(length = 32)
    private String lastName;

    /**
     * The email address of the customer.
     * This field is required.
     */
    @Column(nullable = false)
    private String email;

    /**
     * The postal address of the customer.
     * This field is optional.
     */
    private String address;

    /**
     * The contact number of the customer.
     * This field is optional.
     */
    @Column(length = 11)
    private String contactNumber;

    /**
     * The URL or path to the customer's profile image.
     * This field is optional.
     */
    private String profileImageUrl;
}
