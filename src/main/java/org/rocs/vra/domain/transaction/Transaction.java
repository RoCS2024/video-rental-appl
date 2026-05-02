package org.rocs.vra.domain.transaction;

import jakarta.persistence.*;
import org.rocs.vra.domain.customer.Customer;
import org.rocs.vra.domain.item.Item;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    @ManyToOne
    private Customer customer;
    @ManyToMany
    private List<Item> items;
    private Date rentDate;
    private Date dueDate;
    private Date returnDate;
}
