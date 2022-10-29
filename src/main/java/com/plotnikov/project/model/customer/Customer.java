package com.plotnikov.project.model.customer;

import com.plotnikov.project.model.Basket;
import com.plotnikov.project.model.Invoice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private CustomerRoles role;

    @OneToOne
    @JoinColumn(name ="basket_id")
    private Basket basket;


    @OneToMany(mappedBy = "customer")
    private List<Invoice> invoices;

}
