package com.plotnikov.project.service;

import com.plotnikov.project.exception.ProductNotFoundException;
import com.plotnikov.project.model.Invoice;
import com.plotnikov.project.model.customer.Customer;
import com.plotnikov.project.model.product.Product;
import com.plotnikov.project.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class InvoiceService{
    private final InvoiceRepository repository;
    private final ProductServiceImpl productService;
    private final CustomerService customerService;
    private final BasketServiceImpl basketService;

    @Autowired
    public InvoiceService(InvoiceRepository repository, ProductServiceImpl productService, CustomerService customerService, BasketServiceImpl basketService) {
        this.repository = repository;
        this.productService = productService;
        this.customerService = customerService;
        this.basketService = basketService;
    }

    public Iterable<Invoice> getAll() {
        return repository.findAll();
    }


    public Invoice create(Principal principal) throws ProductNotFoundException {
        Customer customer = customerService.getCustomerByPrincipal(principal);
        List<Product> productsInBasket = customer.getBasket().getProductsInBasket();
        Invoice invoice = new Invoice();
        for(Product product:productsInBasket){
            productService.findById(product.getId()).orElse(null).setCount(product.getCount()-1);
            if (product.getCount()<0){
                log.info("We have not more products with id" + product.getId());
                throw new ProductNotFoundException("We don't have product " + product.getName() + " with this count");
            }
        }
        productsInBasket =  productService.saveAll(productsInBasket);
        invoice.setProductsInInvoice(productsInBasket);
        invoice.setSum(productsInBasket.stream().mapToInt(Product::getPrice).sum());
        invoice.setCustomer(customer);
        invoice = repository.save(invoice);
        customer.getInvoices().add(invoice);
        customerService.save(customer);
        basketService.clearBasket(principal);
        return invoice;
    }

    public void delete(Invoice invoice) {
        repository.delete(invoice);
    }

    public void deleteById(String id) {
        Invoice invoice = repository.findById(id).get();
        repository.delete(invoice);
    }

    public List<Invoice> findByCustomer_Email(String email) {
        List<Invoice> invoices = new ArrayList<>();
        Customer customer = customerService.findByEmail(email);
        if (customer != null) {
            invoices = customer.getInvoices();
        }
        return invoices;
    }

    public Invoice findById(String id){
        return repository.findInvoiceById(id);
    }
}
