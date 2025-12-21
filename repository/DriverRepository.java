package repository;

import models.Driver;
import java.util.Collection;
import java.util.List;

public interface DriverRepository {
    void save(Driver driver);
    Driver findById(String id);
    Collection<Driver> findAll();

}
