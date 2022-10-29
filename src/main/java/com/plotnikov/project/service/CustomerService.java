package com.plotnikov.project.service;

import com.plotnikov.project.model.customer.Customer;
import com.plotnikov.project.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class CustomerService {

    private CustomerRepository repository;

    @Autowired
    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }


    public Customer save(Customer customer){
        return repository.save(customer);
    }

    public Customer findById(String id){
        return repository.findById(id).get();
    }
    public Customer findByEmail(String email){return repository.findByEmail(email).orElse(null);}
    public List<Customer> findByName(String firstName, String lastName){return repository.findAllByFirstNameLikeOrLastNameLike(firstName, lastName);}
    public void delete(String id){
        repository.deleteById(id);
    }
    public Iterable<Customer> getAll(){
        return repository.findAll();
    }

    public Customer getCustomerByPrincipal(Principal principal){
        if(Objects.isNull(principal)){
            return null;
        }
        return findByEmail(principal.getName());
    }


}
