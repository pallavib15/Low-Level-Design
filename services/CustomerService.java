package services;

import models.Customer;
import repository.CustomerRepository;

public class CustomerService {
        private final CustomerRepository repo;

        public CustomerService(CustomerRepository repo) {
            this.repo = repo;
        }

        public void onboardCustomer(String id, String name) {
            repo.save(new Customer(id, name));
            System.out.println("Customer onboarded: " + id);
        }

}
