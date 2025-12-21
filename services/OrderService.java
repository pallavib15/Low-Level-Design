package services;

import models.*;
import repository.CustomerRepository;
import repository.DriverRepository;
import repository.OrderRepository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class OrderService {
        private final OrderRepository orderRepo;
        private final CustomerRepository customerRepo;
        private final DispatcherService dispatcher;
        private final NotificationService notificationService;
        private final ItemCatalog itemCatalog;
        private final DriverRepository driverRepository;

        private final ExecutorService executor= Executors.newFixedThreadPool(5);

        public OrderService(OrderRepository orderRepo,
                            CustomerRepository customerRepo,
                            DispatcherService dispatcher,
                            NotificationService notificationService,
                            ItemCatalog itemCatalog,
                            DriverRepository driverRepository) {
            this.orderRepo = orderRepo;
            this.customerRepo = customerRepo;
            this.dispatcher = dispatcher;
            this.notificationService = notificationService;
            this.itemCatalog = itemCatalog;
            this.driverRepository = driverRepository;

        }



        public void processOrder(String customerId, String ItemId)  {

            for(int i=0;i<5;i++){
                executor.submit(()-> createOrder(customerId,ItemId));
            }
        }
        public void shutdown(){
            executor.shutdown();
        }

        public String createOrder(String customerId, String itemId) {
            if(!itemCatalog.isValidItem(itemId)){
                throw new IllegalArgumentException("Item not valid"+itemId);
            }
            if (customerRepo.findById(customerId) == null)
                throw new IllegalStateException("Customer not onboarded");

            Order order = new Order(UUID.randomUUID().toString(), customerId, itemId);

            orderRepo.save(order);
            dispatcher.tryAssign(order);
            notificationService.notifyCustomer(customerId, "Order created and OrderId is: " +order.getOrderId() );
            return order.getOrderId();
        }

       public void cancelOrder(String orderId) {
            Order order = orderRepo.findById(orderId);
            synchronized (order) {
                String driverId = order.getDriverId();
                order.cancel();


                if (driverId != null) {
                    driverRepository.findById(driverId).complete(0);
                    dispatcher.tryNextAssign();
                }
            }

            notificationService.notifyCustomer(
                    order.getCustomerId(),
                    "Order canceled " + orderId
            );



        }

        public OrderStatus getStatus(String orderId) {

            return orderRepo.findById(orderId).getStatus();
        }

        public Order getOrder(String orderId) {
            return orderRepo.findById(orderId);
        }


}
