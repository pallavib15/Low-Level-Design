package repository;

import models.Driver;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryDriver implements DriverRepository
{
    private final Map<String, Driver> driverMap = new ConcurrentHashMap<>();

    public void save(Driver d) {
        if (driverMap.putIfAbsent(d.getDriverId(), d) != null)
            throw new IllegalStateException("Driver already exists");
    }

    public Driver findById(String id) {
        return driverMap.get(id);
    }

    public Collection<Driver> findAll() {
        return driverMap.values();
    }
}
