package services;

public class PushNotification implements  NotificationService{


    @Override
    public void notifyCustomer(String customerId, String message) {
        System.out.println("PUSH Notification for customer "+customerId+" "+ message);
    }

    @Override
    public void notifyDriver(String driverId, String message) {
        System.out.println("PUSH Notification for driver "+driverId+message);
    }
}
