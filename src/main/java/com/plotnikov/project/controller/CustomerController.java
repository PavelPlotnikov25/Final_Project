package com.plotnikov.project.controller;

import com.plotnikov.project.model.customer.Customer;
import com.plotnikov.project.model.customer.CustomerRoles;
import com.plotnikov.project.service.BasketServiceImpl;
import com.plotnikov.project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class CustomerController {

    private final CustomerService service;
    private final PasswordEncoder passwordEncoder;

    private final BasketServiceImpl basketService;

    @Autowired
    public CustomerController(CustomerService service, PasswordEncoder passwordEncoder, BasketServiceImpl basketService) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
        this.basketService = basketService;
    }


    @GetMapping("/registration")
    public ModelAndView registration(ModelAndView modelAndView) {
        modelAndView.addObject("user", new Customer());
        modelAndView.setViewName("registration");
        modelAndView.addObject("roles", List.of(CustomerRoles.values()));
        return modelAndView;
    }

    @PostMapping("/registration")
    public ModelAndView registration(
            @ModelAttribute Customer customer,
            ModelAndView modelAndView) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setBasket(basketService.createBasket());
        service.save(customer);
        modelAndView.setViewName("redirect:" + "/login");
        return modelAndView;
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public ModelAndView getUsers(ModelAndView modelAndView) {
        List<Customer> customers = StreamSupport.stream(service.getAll().spliterator(), false).collect(Collectors.toList());
        modelAndView.setViewName("allUsers");
        return modelAndView.addObject("customers", customers);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/profile/{customerId}")
    public ModelAndView getCustomerInfo(@PathVariable String customerId,
                                        ModelAndView modelAndView){
        Customer foundCustomer = service.findById(customerId);
        modelAndView.addObject("foundCustomer", foundCustomer);
        modelAndView.setViewName("customer");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/users/delete")
    public ModelAndView delete(@ModelAttribute Customer customer, ModelAndView modelAndView){
        service.delete(customer.getId());
        modelAndView.setViewName("redirect:" + "/users");
        return modelAndView;
    }
}
