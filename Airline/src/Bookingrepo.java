import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Bookingrepo {

    ConcurrentHashMap<String,Booking> bookingMap = new ConcurrentHashMap<>();

    public void save(Booking booking){
        bookingMap.put(booking.getUserId(), booking);
    }

    public Booking getBookingById(String userId){
        return bookingMap.get(userId);
    }

    public List<Booking> findAll(){
        return (List<Booking>) bookingMap.values();
    }
}
