public class PaymentByUPI implements PaymentStrategy {

    @Override
    public synchronized PaymentStatus pay(int amount, String flightId,String passengerId) {
        if(amount<0){
            return PaymentStatus.FAILED;
        }
        return PaymentStatus.SUCCESS;
    }
}
