package com.rocs.vra.domain.customer;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class Customer implements Serializable {
    @Id
    private String id;
    @Column(length = 64)
    private String firstName;
    @Column(length = 32)
    private String middleName;
    @Column(length = 32)
    private String lastName;
    private String email;
    private String address;
    @Column(length = 11)
    private String contactNumber;
    private String profileImageUrl;
}
