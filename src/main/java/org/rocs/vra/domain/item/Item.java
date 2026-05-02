package org.rocs.vra.domain.item;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents an item entity persisted in the database.
 * This class typically models a library item, media product, or any inventory item
 * that has a title, genre, copy count, and creation date.
 */
@Entity
@Data
public class Item implements Serializable {

    /**
     * The unique identifier of the item.
     * This field serves as the primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    /**
     * The title of the item.
     * Maximum length is 64 characters.
     */
    @Column(length = 64, nullable = false)
    private String title;

    /**
     * The genre or category of the item.
     */
    private String genre;

    /**
     * The number of available copies of the item.
     */
    private int copies;

    /**
     * The date when the item was created or added to the system.
     */
    private Date dateCreated;
}
