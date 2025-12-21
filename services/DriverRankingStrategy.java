package services;

import models.Driver;

import java.util.Collection;
import java.util.List;

public interface DriverRankingStrategy {
    List<Driver> rank(List<Driver> drivers);
}
