public interface PaymentStrategy {

    public PaymentStatus pay(int amount, String flightId,String passengerId);
}
