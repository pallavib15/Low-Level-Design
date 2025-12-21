package repository;

import models.Customer;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCustomer implements CustomerRepository{
        private final ConcurrentHashMap<String,Customer> customerMap=new ConcurrentHashMap<>();

    @Override
    public void save(Customer customer) {
        if(customerMap.putIfAbsent(customer.getId(),customer)!=null) {
            throw new IllegalArgumentException("Customer already exists");
        }else  {
            customerMap.put(customer.getId(),customer);
        }
    }

    @Override
    public Customer findById(String id) {
        return customerMap.get(id);
    }

    @Override
    public List<Customer> findAll() {
        return (List<Customer>) customerMap.values();
    }

}
