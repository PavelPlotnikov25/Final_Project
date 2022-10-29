package com.plotnikov.project.repository;

import com.plotnikov.project.model.customer.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String > {

    List<Customer> findAllByFirstNameLikeOrLastNameLike(String firstName, String LastName);

    Optional<Customer> findByEmail(String email);

    void deleteById(String id);

}
