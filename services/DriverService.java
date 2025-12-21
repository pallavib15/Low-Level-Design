package services;

import models.Driver;
import models.DriverStatus;
import models.Order;
import models.OrderStatus;
import repository.DriverRepository;

public class DriverService {
        private final DriverRepository repo;
        private final NotificationService notificationService;

        public DriverService(DriverRepository repo,NotificationService notificationService) {
            this.repo = repo;
            this.notificationService = notificationService;
        }

        public void onboardDriver(String id, String name) {
            repo.save(new Driver(id, name));
            System.out.println("Driver onboarded: " + id);
        }

        public void pickUp(String driverId, Order order) {
            synchronized (order) {
                if (order.getStatus() != OrderStatus.ASSIGNED)
                    throw new IllegalStateException("Order not assigned");
                if (!driverId.equals(order.getDriverId()))
                    throw new IllegalStateException("Assigned wrong driver");


                repo.findById(driverId).pickUp();
                order.markPickedUp();
            }

            notificationService.notifyCustomer(order.getCustomerId(),"Order picked by driver"+ driverId
            );
        }

        public void complete(String driverId, Order order, double rating) {
            repo.findById(driverId).complete(rating);
            order.markDelivered();

            notificationService.notifyDriver(
                    driverId,
                    "Order completed. Rating received: " + rating
            );
        }

        public DriverStatus getStatus(String driverId) {
            return repo.findById(driverId).getStatus();
        }
}
