package com.plotnikov.project.model.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String name;

    private int price;

    private int count;

    private String description;

    private ProductType productType;

    private ProductTechnology productTechnology;

    private String imgUrl;

//    @ManyToMany(mappedBy = "product")
//    private List<Invoice> invoices;


}
