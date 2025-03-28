package edu.byui.apj.storefront.tutorial101;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);

    Customer findById(long id);

    List<Customer> findByFirstNameIgnoreCaseOrLastNameIgnoreCase(String firstName, String lastName);
}