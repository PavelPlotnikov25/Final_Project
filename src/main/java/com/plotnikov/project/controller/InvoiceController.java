package com.plotnikov.project.controller;


import com.plotnikov.project.exception.ProductNotFoundException;
import com.plotnikov.project.model.Invoice;
import com.plotnikov.project.model.customer.Customer;
import com.plotnikov.project.model.product.Product;
import com.plotnikov.project.service.BasketServiceImpl;
import com.plotnikov.project.service.CustomerService;
import com.plotnikov.project.service.InvoiceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    private final BasketServiceImpl basketService;
    private final CustomerService customerService;

    private final InvoiceService invoiceService;

    public InvoiceController(BasketServiceImpl basketService, CustomerService customerService, InvoiceService invoiceService) {
        this.basketService = basketService;
        this.customerService = customerService;
        this.invoiceService = invoiceService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{invoiceId}")
    public ModelAndView getInvoice(ModelAndView modelAndView,
                                   @PathVariable String invoiceId){
        Invoice invoice = invoiceService.findById(invoiceId);
        Map<Product, Integer> productIntegerMap = new HashMap<>();
        int sum = 0;
        for (Product product : invoice.getProductsInInvoice()) {
            if (productIntegerMap.containsKey(product)) {
                productIntegerMap.put(product, productIntegerMap.get(product) + 1);
            } else {
                productIntegerMap.put(product, 1);
            }
        }
        modelAndView.addObject("productsMap", productIntegerMap.entrySet());
        modelAndView.addObject("invoice", invoice);;
        modelAndView.setViewName("invoice");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/create")
    public ModelAndView createInvoice(ModelAndView modelAndView,
                                      Principal principal){
        Invoice invoice = null;
        try {
            invoice = invoiceService.create(principal);
        } catch (ProductNotFoundException e) {
            e.getMessage();
        }
        String invoiceId = invoice.getId();
        modelAndView.addObject("invoice", invoice);
        modelAndView.setViewName("redirect:" + "/invoice/" + invoiceId);
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{userId}/allInvoices")
    public ModelAndView getInvoicesByUser(ModelAndView modelAndView,
                                          @PathVariable String userId){
        List<Invoice> invoices = customerService.findById(userId).getInvoices();
        modelAndView.addObject("user", customerService.findById(userId));
        modelAndView.addObject("userInvoices", invoices);
        modelAndView.setViewName("userInvoices");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/my-invoices")
    public ModelAndView getMyInvoices(ModelAndView modelAndView,
                                          Principal principal){
        Customer customer = customerService.getCustomerByPrincipal(principal);
        List<Invoice> invoices = customer.getInvoices();
        modelAndView.addObject("user", customer);
        modelAndView.addObject("userInvoices", invoices);
        modelAndView.setViewName("userInvoices");
        return modelAndView;
    }
}
