package com.rocs.vra.domain.transaction;

import com.rocs.vra.domain.customer.Customer;
import com.rocs.vra.domain.item.Item;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Transaction implements Serializable {
    @Id
    private Long id;
    @ManyToOne
    private Customer customer;
    @ManyToMany
    private List<Item> items;
    private Date rentDate;
    private Date dueDate;
    private Date returnDate;
}
