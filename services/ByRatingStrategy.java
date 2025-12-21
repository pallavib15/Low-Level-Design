package services;

import models.Driver;

import java.util.List;

public class ByRatingStrategy implements DriverRankingStrategy {


    @Override
    public List<Driver> rank(List<Driver> drivers) {
        return drivers.stream()
                .sorted((a,b)->Double.compare(
                        b.getAverageRating(),a.getAverageRating()
                )).toList();
    }
}
