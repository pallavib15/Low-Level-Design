package repository;

import models.Order;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOrder implements OrderRepository{

private final Map<String,Order> orderMap=new ConcurrentHashMap<>();
    @Override
    public void save(Order order) {
       orderMap.put(order.getOrderId(),order);
    }

    @Override
    public Order findById(String id) {
        return orderMap.get(id);
    }

    @Override
    public Collection<Order> findAll() {
        return orderMap.values();
    }
}
