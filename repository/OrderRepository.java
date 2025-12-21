package repository;

import models.Order;

import java.util.Collection;

public interface OrderRepository {
    void save(Order order);
    Order findById(String id);
    Collection<Order> findAll();
}
