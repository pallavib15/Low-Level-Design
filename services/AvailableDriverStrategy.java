package services;

import models.Driver;

import java.util.Collection;
import java.util.Optional;

public class AvailableDriverStrategy implements DriverSelectionStrategy {


    @Override
    public Optional<Driver> selectDriver(Collection<Driver> drivers) {
        return drivers.stream().filter(Driver::isAvailable).findFirst();
    }
}
