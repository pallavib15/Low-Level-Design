package models;

public class Order {

        private final String orderId;
        private final String customerId;
        private final String itemId;
        private final long createdAt;

        private String driverId;
        private OrderStatus status = OrderStatus.CREATED;

        public Order(String orderId, String customerId, String itemId) {
            this.orderId = orderId;
            this.customerId = customerId;
            this.itemId = itemId;
            this.createdAt = System.currentTimeMillis();
        }


       public synchronized void assignOrder(String driverId) {
            if (status != OrderStatus.CREATED)
                throw new IllegalStateException("Order not assignable");
            this.driverId = driverId;
            this.status = OrderStatus.ASSIGNED;
        }

       public synchronized void markPickedUp() {
            if (status != OrderStatus.ASSIGNED)
                throw new IllegalStateException("Pickup not allowed");
            status = OrderStatus.PICKED_UP;
        }

       public synchronized void markDelivered() {

            status = OrderStatus.DELIVERED;
        }

        public synchronized void cancel() {
            if (status == OrderStatus.PICKED_UP || status == OrderStatus.DELIVERED)
                throw new IllegalStateException("Cannot cancel after pickup");
            status = OrderStatus.CANCELED;
        }

        public String getOrderId() {
            return orderId;
        }
        public String getCustomerId() {
            return customerId;
        }
        public String getDriverId() {
            return driverId;
        }
        public OrderStatus getStatus() {
            return status;
        }
        public long getCreatedAt() {
            return createdAt;
        }
    }


