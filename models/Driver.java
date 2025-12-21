package models;

public class Driver {
    private final String driverId;
    private final String name;

    public String getName() {
        return name;
    }

    public double getTotalRating() {
        return totalRating;
    }

    private DriverStatus status = DriverStatus.AVAILABLE;
    private int completedOrders = 0;
    private double totalRating = 0;

    public Driver(String driverId, String name) {
        this.driverId= driverId;
        this.name = name;
    }

   public synchronized void assign() {
        if (status != DriverStatus.AVAILABLE)
            throw new IllegalStateException("Driver not available");
        status = DriverStatus.ASSIGNED;
    }

   public synchronized void pickUp() {
        if (status != DriverStatus.ASSIGNED)
            throw new IllegalStateException("Invalid pickup");
        status = DriverStatus.PICKED_UP;
    }

   public synchronized void complete(double rating) {
        status = DriverStatus.AVAILABLE;
        completedOrders++;
        totalRating += rating;
    }


    public boolean isAvailable() {
        return status == DriverStatus.AVAILABLE;
    }
    public DriverStatus getStatus() {
        return status;
    }
    public String getDriverId() {
        return driverId;
    }
    public double getAverageRating() {
        return completedOrders == 0 ? 0 : totalRating / completedOrders;
    }
    public int getCompletedOrders() { return completedOrders; }
}

