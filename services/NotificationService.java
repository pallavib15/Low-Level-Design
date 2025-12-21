package services;

public interface NotificationService {
    public void notifyCustomer(String customerId,String message);
    public void notifyDriver(String driverId,String message);
}


