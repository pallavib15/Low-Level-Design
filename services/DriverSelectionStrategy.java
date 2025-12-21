package services;

import models.Driver;

import java.util.Collection;
import java.util.Optional;

public interface DriverSelectionStrategy {

    Optional<Driver> selectDriver(Collection<Driver> drivers);
}
