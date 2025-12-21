import java.util.List;

public class BookingService {

    FlightService flightservice;
    SearchService searchService;
    Bookingrepo bookingRepo;

    public BookingService(FlightService flightservice, SearchService searchService,Bookingrepo bookingrepo) {
        this.flightservice=flightservice;
        this.searchService=searchService;
        this.bookingRepo=bookingrepo;
    }

    public void bookFlight(String flightId, List<Seat> seats){
        Flight f= flightservice.getFlight(flightId);
        for(Seat seat:seats){
            f.bookSeat(seat);
        }
    }

    public Booking confirmBooking(Flight flight,String passengerId){
        flight.reserveSeat();
        Booking booking = new Booking();
        booking.setBookingId(passengerId+"_"+flight.getFlightNo());
        booking.setBookingStatus(BoookingStatus.BOOKED);
        booking.setUserId(passengerId);
        booking.setArrivalTime(flight.getStartTime());
        booking.setDepartureTime(flight.getEndTime());
        return  booking;
    }

    public PaymentStatus pay(PaymentStrategy paymentStrategy,int amount, String passengerId ,String flightId){
       return paymentStrategy.pay(amount,passengerId,flightId);
    }

    public void cancelBooking(String bookingId) {
        Booking booking = bookingRepo.getBookingById(bookingId);
        Flight f = flightservice.getFlight(booking.getFlightId());

        List<Seat> seats = f.getSelectedSeats();
        for (Seat seat : seats) {
            f.cancelSeat(seat.getSeatId());
        }
        booking.setBookingStatus(BoookingStatus.CANCELLED);
    }
}
