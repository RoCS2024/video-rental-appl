package com.rocs.vra.domain.item;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Item implements Serializable {
    @Id
    private String id;
    @Column(length = 64)
    private String title;
    private String genre;
    private int copies;
    private Date dateCreated;
}
