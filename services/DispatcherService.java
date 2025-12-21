package services;

import models.Driver;
import models.Order;
import models.OrderStatus;
import repository.DriverRepository;
import repository.OrderRepository;

import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DispatcherService {
        private final DriverSelectionStrategy strategy;
        private final DriverRepository driverRepo;
        private final Lock lock = new ReentrantLock();
        private final OrderRepository  orderRepo;

        public DispatcherService(DriverSelectionStrategy strategy,
                                 DriverRepository driverRepo,
                                 OrderRepository orderRepo) {
            this.strategy = strategy;
            this.driverRepo = driverRepo;
            this.orderRepo = orderRepo;
        }

      public void tryAssign(Order order) {
            lock.lock();
            try {
                Optional<Driver> driverOpt =
                        strategy.selectDriver(driverRepo.findAll());

                if (driverOpt.isEmpty()) return;

                Driver driver = driverOpt.get();
                driver.assign();
                order.assignOrder(driver.getDriverId());

            } finally {
                lock.unlock();
            }
        }


        public void tryNextAssign(){
            lock.lock();
            try {
                Optional<Driver> freeDriver =
                        strategy.selectDriver(driverRepo.findAll());
                if (freeDriver.isEmpty()) return;

                for (Order o : orderRepo.findAll()) {
                    if (o.getStatus() == OrderStatus.CREATED) {
                        freeDriver.get().assign();
                        o.assignOrder(freeDriver.get().getDriverId());
                        break;
                    }
                }
            } finally {
                lock.unlock();
            }
        }
}
