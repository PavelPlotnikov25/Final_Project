package com.plotnikov.project.model;

import com.plotnikov.project.model.customer.Customer;
import com.plotnikov.project.model.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private int sum;

//    @ManyToMany
//    @JoinTable(name = "invoice_product",
//    joinColumns = @JoinColumn(name = "product_id"),
//    inverseJoinColumns = @JoinColumn(name = "invoice_id"))
//    private List<Product> products;

    @ManyToMany
    @JoinTable(name = "product_invoice",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "invoice_id"))
    private List<Product> productsInInvoice;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return id.equals(invoice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
