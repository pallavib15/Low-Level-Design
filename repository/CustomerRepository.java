package repository;

import models.Customer;

import java.util.List;

public interface CustomerRepository {
    void save(Customer customer);
    Customer findById(String id);

    List<Customer> findAll();

}
