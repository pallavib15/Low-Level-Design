package services;

import models.Driver;
import repository.DriverRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DashboardService {
    private final DriverRepository repo;
    public DashboardService(DriverRepository r) { repo = r; }

    public List<Driver> topDrivers(DriverRankingStrategy s) {
        List<Driver> drivers = new ArrayList<>(repo.findAll());

        return s.rank(drivers);
    }

}
